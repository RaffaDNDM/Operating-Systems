package Airport;

import os.Util;

public class AereoCheAtterra extends Thread
{
   private int num;
   private Airport tc;
   private final static long ATTERRAGGIOA = 1000L;
   private final static long ATTERRAGGIOB = 2000L;
   
   public AereoCheAtterra(int num, Airport tc)
   {
      this.num = num;
      this.tc = tc;
   }

   public void run()
   {
      tc.richAutorizAtterraggio(num);
      Util.sleep(ATTERRAGGIOA);
      tc.freniAttivati(num);
      Util.sleep(ATTERRAGGIOB);
      tc.inParcheggio(num);
   }
}