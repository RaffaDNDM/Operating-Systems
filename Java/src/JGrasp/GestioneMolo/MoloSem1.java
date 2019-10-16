package GestioneMolo;

import os.Semaphore;
// import os.Mailbox;
import os.Util;
// import os.Sys;

/**{c}
 * Molo soluzione con semafori
 * @author M.Moro DEI UNIPD
 * @author C.Ferrari DEI UNIPD
 * @version 1.00 2016-06-28
 */

public class MoloSem1
{
    private int truckD=0, truckS=0;
        // conteggio attese
    private boolean puntoDiCarico[] = new boolean[3];
        // stato sportelli
    private boolean puntoAttrezzato[] = new boolean[3];
        // stato gru

    private Semaphore mutex = new Semaphore(true);
      // protezione della sezione critica
    private Semaphore att[] = new Semaphore[2];
      // semafori privati
    private Semaphore stopGru[] = new Semaphore[2];
    private Semaphore servito[] = new Semaphore[3];
    private long tMin, tMax;
        // range durata servizio


    /**[c]
     * @param minT  minima durata scarico
     * @param maxT  massima durata scarico
     */
    public MoloSem1(long minT, long maxT)
    {
        for (int i=0; i<3; puntoDiCarico[i++]=true);
        att[0] = new Semaphore(0, 1);
        att[1] = new Semaphore(0, 1);
        stopGru[0] = new Semaphore(0, 1);
        stopGru[1] = new Semaphore(0, 1);
        servito[0] = new Semaphore(0, 1);
        servito[1] = new Semaphore(0, 1);
        servito[2] = new Semaphore(0, 1);
        tMin=minT;
        tMax=maxT;
    } //[c]


    /**{c}
     * thread Gru
     */
    private class Gru extends Thread
    {
     private int idg;
     /**[c]
         * @param idg  indice gru
         */
        public Gru(int id)
        {
            super("Gru"+id);
            System.out.println("Crea "+getName());
            this.idg = id;
        }

        /**[m]
         * rappresenta una Gru sul molo
         */
        public void run()
        {
         int left=0, right=1;

         System.out.println("Attivata Gru "+getName());

         if ( idg == 1 ) { left=0; right=1; }
         else if (idg == 2 ) {left =1; right=2; }

         while(true) {
                   stopGru[idg-1].p();
                   for ( int j=left ; j<= right ; j++ ) {
                       mutex.p();
                       if ( !puntoDiCarico[j] ) {
                         mutex.v();
                         Util.rsleep(tMin, tMax);
                         servito[j].v();
                         break;
                        }
                       else mutex.v();
                   }
         } //end while
       } //[m] run
      } //{c} Gru


    /**{c}
     * thread Camion
     */
    private class Camion extends Thread
    {
        private boolean tipoMerce;
          // tipo utente
        private int idx;
          // indice utente

        /**[c]
         * @param tipoMerceA  true se container deperibile,  false altrimenti
         * @param id  indice camion
         */
        public Camion(boolean tipoMerce, int id)
        {
            super("Camion"+id);
            System.out.println("Crea "+getName());
            this.tipoMerce = tipoMerce;
            this.idx = idx;
        }

        /**[m]
         * rappresenta un camion sul molo
         */
        public void run()
        {
         int posto;
         System.out.println("Attivato "+getName());
         posto=entra(tipoMerce);
         System.out.println("** "+getName()+" inizia il servizio su posto="+posto);

         servito[posto].p();

         System.out.println("**** "+getName()+" fine servizio su posto="+posto);
         esce(posto);
        } //[m] run
    } //{c} Camion

    /**[m]
     * entra
     * @param richiestA  true se di merce di tipo A, false altrimenti
     * @return  punto assegnato,
     */
    int entra(boolean richiestaA)
    {

     mutex.p();

        if (richiestaA)
        { // merce di tipo Standard
          if (!puntoDiCarico[1] && !puntoDiCarico[2])
            {
             truckS++;
             mutex.v();
             att[0].p();  // si sospende sul semaforo privato
             // riceve la mutua esclusione
             truckS--;
            }
            for (int i=1; i<=2; i++)
            {
             if (puntoDiCarico[i])
               {
                puntoDiCarico[i] = false;
                if ( i==2 ) stopGru[1].v();
                else if ( !puntoDiCarico[0] ) stopGru[1].v();
                else if (  puntoDiCarico[2] ) stopGru[1].v();
                else stopGru[0].v();
                mutex.v();
                return i;
               }
            } // for
            // qui non dovrebbe mai capitare
            System.out.println("???????? "+Thread.currentThread().getName()+" errore risveglio!");
            mutex.v();
        }
        else
        { // merce di tipo Deperibile
           if (!puntoDiCarico[0])
            {
             truckD++;
             mutex.v();
             att[1].p();  // si sospende sul semaforo privato
             // riceve la mutua esclusione
             truckD--;
            }
            puntoDiCarico[0] = false;
            stopGru[0].v();
            mutex.v();
            return 0;
        }
        return 0;
    } //[m] entra

    /**[m]
     * esce
     * @param punto  punto di carico da abbandonare
     */
    void esce(int punto)
    {
        mutex.p();
        puntoDiCarico[punto]=true;
        if (punto==0 && truckD>0)
            // priorita', cede mutex
            att[1].v();
        else if (punto!=0 && truckS>0)
            // cede mutex
            att[0].v();
        else
            mutex.v();
    }


    /**[m][s]
     * main di collaudo
     * @param args[0] tMin
     * @param args[1] tMax
     */
    public static void main(String[] args)
    {
        System.err.println("** Battere Ctrl-C per terminare!");
        MoloSem1 ml = new MoloSem1(Long.parseLong(args[0]),
          Long.parseLong(args[1]));

        for (int k=1 ; k<3; k++ )
        {
         ml.new Gru(k).start();
        }

        for (int i=1; i<=200; i++)
        {
            Util.rsleep(1000L, 3000L);
            ml.new Camion(Util.randVal(1, 4)!=4, i).start();
        } // for
    } //[m][s] main

} //{c} Molo
