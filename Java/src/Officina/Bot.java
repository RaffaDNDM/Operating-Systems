/**
@author Di Nardo Di Maio Raffaele
*/

package Officina;

import os.Util;

public class Bot extends Thread
{
   private int id;
   private int type;
   private long minT;
   private long maxT;
   private Officina o;

   public Bot(int id, int type, long minT, long maxT, Officina o)
   {
      this.id = id;
      this.minT = minT;
      this.maxT = maxT;
      this.type = type;
      this.o = o;
   }

   public void run()
   {
      int pos = o.entra(type);
      Util.rsleep(minT, maxT);
      o.esce(type, pos);
   }
}
