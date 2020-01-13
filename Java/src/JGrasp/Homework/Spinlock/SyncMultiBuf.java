/**
   Changes to the implementation to guarantee the use of
   Spinlock and not of number semaphore in os.Semaphore
   @author Di Nardo Di Maio Raffaele 1204879

   ---------------------------------------------------------------------------------
                                    Traccia 5
   ---------------------------------------------------------------------------------
   Realizzare la classe Spinlock che implementa il semaforo numerico in una forma
   con parziale busy-waiting. Si forniscano i metodi p(), p(long timeout), v()
   come nella classe Semaphore. Il thread che deve attendere si spospende con uno
   sleep per un tempo variabile prima di rivalutare la condizione sul semaforo. Per
   ridurre il busy waiting tale tempo vale, nell'ordine, 8*T, 4*T, 2*T, 1*T, 8*T,
   4*T ecc. ciclicamente finchè l'attesa termina con la condizione verificata. T sia
   una costante temporale adeguatamente impostata.
   Collaudare la classe Spinlock con un modello Produttore/Consumatore a buffer
   multiplo utilizzando le classi Producer, Consumer, TestPC della libreria.
   ---------------------------------------------------------------------------------
*/

package Spinlock;

import os.Semaphore;
import os.Buffer;
import os.Timeout;

/**{c}
 * buffer multiplo con 
 * 2 semafori numerici e un mutex di protezione 
 * @author M.Moro DEI UNIPD
 * @version 1.00 2002-04-17
 * @version 2.00 2003-10-02 package Os
 * @version 2.01 2005-10-07 package os
 */
 
public class SyncMultiBuf implements Buffer
{
    private Spinlock spaceAval;
      // locazioni disponibili
    private Spinlock dataAval;
      // dati disponibili
    private Semaphore mutex = new Semaphore(true);
      // mutua esclusione
    private Object data[];
      // buffer dati
    private int numEl, head=0, tail=0;
      // numero elementi, dove si legge, dove si scrive
      
      
    /**[c]
     * inizializza al numero di elementi indicati
     * @param n  numero elementi del buffer
     */
    public SyncMultiBuf(int n)
    {
      numEl = n;
    	spaceAval = new Spinlock(numEl);
      dataAval = new Spinlock(0, numEl);
      data = new Object[numEl];
    } 
    
    /**[m]
     * prelievo del dato
     * @param timeout  scadenza d'attesa
     * @return dato prelevato se in tempo
     *         oggetto EXPIREDOBJ se timeout
     */
    public Object read(long timeout)
    {
        if (dataAval.p(timeout) == Timeout.EXPIRED)
            return Timeout.EXPIREDOBJ;
        // un dato e` disponibile
        mutex.p();  // mutex
        Object ret = data[head];
        data[head] = null;
        head = (++head) % numEl;
        System.out.println("-- "+Thread.currentThread().getName()+
          " ha letto "+ret+" nel buffer ora="+dataAval.value());
        mutex.v();
        spaceAval.v();  // segnala spazio disponibile
        return ret;
    }

    /**[m]
     * prelievo del dato
     */
    public Object read()
    {  return read(Timeout.NOTIMEOUT); }

    /**[m]
     * deposito del dato
     * @param d  dato da memorizzare
     * @param timeout  scadenza d'attesa
     * @return dato inserito se in tempo
     *         oggetto EXPIREDOBJ se timeout
     */
    public Object write(Object d, long timeout)
    {
        if (spaceAval.p(timeout) == Timeout.EXPIRED)
            return Timeout.EXPIREDOBJ;
            
        // spazio disponibile
        mutex.p();  // mutex
        data[tail] = d;
        tail = (++tail) % numEl;
        System.out.println("** "+Thread.currentThread().getName()+
          " ha scritto "+d+" nel buffer ora="+(dataAval.value()+1));
        mutex.v();
        dataAval.v();  // segnala un dato disponibile
        return d;
    }

    /**[m]
     * deposito del dato
     * @param d  dato da memorizzare
     */
    public void write(Object d)
    {  write(d, Timeout.NOTIMEOUT); }

    /**[m}
     * dati memorizzati
     * @return numero dati memorizzati
     */
    public int size()
    { return dataAval.value(); }
    
    /**[m}
     * spazio allocato
     * @return numero elementi allocati
     */
    public int dimen()
    { return numEl; }
    
} //{c} SyncMultiBuf

