package Airport;

import os.Util;

public class AereoCheDecolla extends Thread
{
   private int num;
   private Airport tc;

   private final static long DECOLLOA = 2000L;
   private final static long DECOLLOB = 1000L;

   public AereoCheDecolla(int num, Airport tc)
   {
      this.num = num;
      this.tc = tc;
   }

   public void run()
   {
      tc.richAccessoPista(num);
      Util.sleep(DECOLLOA);
      tc.richAutorizDecollo(num);
      Util.sleep(DECOLLOB);
      tc.inVolo(num);
   }
}
   
   