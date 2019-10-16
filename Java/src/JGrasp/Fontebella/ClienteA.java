package Fontebella;

import os.Util;

public class ClienteA extends Thread
{
   private static final long RIEMPIMENTO = 15500L; // tempo di riempimento
   private int num;
   private Fontebella f;

   public ClienteA(int num, Fontebella f)
   {
      this.num = num;
      this.f = f;
   }
   
   public void run()
   {
      int zamp = f.entraCodaA();
      Util.sleep(RIEMPIMENTO);
      f.fineRiempimento();
   }
}