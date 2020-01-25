/**
   @author Di Nardo Di Maio Raffaele
*/

package Crossroad;

import os.Util;

public class Car extends Thread
{
   //min crossing time for the car
   private final static long MIN_TIME = 2000L;
   //max crossing time for the car
   private final static long MAX_TIME = 5000L;

   //identifier of the car
   private int id;
   //crossroad instance
   private Crossroad cr;

   /**
      Car in the crossroad
      @param id identifier of the car
      @param cr crossroad instance
   */
   public Car(int id, Crossroad cr)
   {
      this.id = id;
      this.cr = cr;
   }

   public void run()
   {
      //true if it will cross along NS direction
      boolean is_NS = (Util.randVal(0,10)>7);

      //arrival to the crossroad and waiting condition if it's occupied
      if(is_NS)
         cr.enterNS();
      else
         cr.enterEW();

      //Simulation of crossing for the car in the crossroad
      Util.rsleep(MIN_TIME, MAX_TIME);

      //Exit from the crossroad
      cr.exit();
   }
}
