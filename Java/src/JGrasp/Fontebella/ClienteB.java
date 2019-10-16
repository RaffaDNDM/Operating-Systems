package Fontebella;

import os.Util;

public class ClienteB extends Thread
{
   private static final long RIEMPIMENTO = 15500L; // tempo di riempimento
   private int num;
   private Fontebella f;

   public ClienteB(int num, Fontebella f)
   {
      this.num = num;
      this.f = f;
   }
   
   public void run()
   {
      int zamp = f.entraCodaB();
      Util.sleep(RIEMPIMENTO);
      f.fineRiempimento();
   }
}