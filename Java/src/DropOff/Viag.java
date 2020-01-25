/**
@author Di Nardo Di Maio Raffaele
*/

package DropOff;

import os.Util;

public class Viag extends Thread
{
   private int num;
   private boolean prio;
   private DropOff d;
   private long minT, maxT; /*min e max tempo di registrazione di un bagaglio*/
   private final static long PRESATIM = 1500L; /*tempo di trasferimento AC BD*/
   private final static long TRASFTIM = 1000L; /*tempo di trasferimento DE (CE = 2*TRASFTIM)*/

   public Viag(int num, boolean prio, DropOff d, long minT, long maxT)
   {
      this.num = num;
      this.prio = prio;
      this.d = d;
      this.minT = minT;
      this.maxT = maxT;
   }

   public void run()
   {
      System.out.println("Il cliente "+num+" e' arrivato "+ (prio?"con ":" senza")+" priorita'");

      int pos = d.inCoda(prio);

      System.out.println("Il cliente "+num+" e' in corso di registrazione al banco "+pos);

      Util.rsleep(minT, maxT);

      System.out.println("Il cliente "+num+" e' stato registrato al banco "+pos);

      d.regok(pos);

      long transf = (pos==0)? 2*TRASFTIM : TRASFTIM;
      Util.sleep(PRESATIM+transf);

      System.out.println("Il trasferimento del bagaglio del cliente "+num+" e' terminato");

      d.term(pos);
   }
}
