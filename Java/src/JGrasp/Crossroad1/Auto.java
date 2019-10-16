package Crossroad;

import os.Util;

public class Auto extends Thread
{
   private int num;
   private Crossroad cs;

   public Auto(int num, Crossroad cs)
   {
      this.num = num;
      this.cs = cs;
   }
   
   public void run()
   {
      int choice = Util.randVal(1, 10);
      
      //0.3 prob di avere macchina su NS altrimenti EW
      String dir = (choice<4)?"N-S":"E-W";
      System.out.println("La macchina "+num+" arriva lungo la direzione "+dir);
      cs.entra(choice<4);
      Util.rsleep(1000L,3000L);
      cs.esce(choice<4);
   }
}