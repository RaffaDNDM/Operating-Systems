package CarWashing;

import os.Util;

public class VeicoloTotale extends Thread
{
   private int num;
   private CarWashing c;
   
   public VeicoloTotale(int num, CarWashing c)
   {
      this.num = num;
      this.c = c;
   }
   
   public void run()
   {
      c.prenotaTotale();
      Util.rsleep(1000L, 1500L);
      Util.rsleep(2000L, 3000L);
      c.lavaInterno();
      Util.rsleep(2000L, 4000L);
      c.pagaTotale();
   }
}