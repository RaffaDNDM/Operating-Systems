package CarWashing;

import os.Util;

public class VeicoloParziale extends Thread
{
   private int num;
   private CarWashing c;
   
   public VeicoloParziale(int num, CarWashing c)
   {
      this.num = num;
      this.c = c;
   }
   
   public void run()
   {
      c.prenotaParziale();
      Util.rsleep(1000L, 1500L);
      Util.rsleep(2000L, 4000L);
      c.pagaParziale();
   }
}