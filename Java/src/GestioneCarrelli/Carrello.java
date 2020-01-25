/**
@author Di Nardo Di Maio Raffaele
*/

package GestioneCarrelli;

import os.Util;

public class Carrello extends Thread
{
   private final static long I1ATT = 1000L;
   private final static long I2ATT = 2000L;
   private final static long BMIN = 2000L;
   private final static long BMAX = 4000L;
   private final static long CMIN = 1000L;
   private final static long CMAX = 4000L;

   private int num;
   private Smista s;
   public final int i;

   public Carrello(int num, Smista s)
   {
      this.s=s;
      this.num=num;
      this.i= Util.randVal(0,2);
   }

   public void run()
   {
      int elevatore = s.instrada(i);
      Util.sleep(I1ATT);
      s.outI1(elevatore);
      Util.rsleep(BMIN, BMAX);
      int d = s.outB(elevatore);
      Util.sleep(I2ATT);
      s.inC(i);
      Util.rsleep(CMIN, CMAX);
      s.outC(i);
   }
}
