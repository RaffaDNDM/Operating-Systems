/**
   @author Di Nardo Di Maio Raffaele

   Test class
*/

package Roundabout;

import java.util.Scanner;
import os.Util;

public class RoundaboutMain
{
   public static void main(String[] args)
   {
      Roundabout r = null;
      Scanner input = new Scanner(System.in);

      int type = 0;

      while(type<1 || type>3)
      {
         System.out.println("Choose method of synchronization:");
         System.out.println("1. Private Semaphore");
         System.out.println("2. Hoare's Monitor");
         System.out.println("3. Java Monitor");

         try
         {
            type = Integer.parseInt(input.nextLine());

            switch(type)
            {
               case 1:
                  r = new RoundaboutSem();
                  break;

               case 2:
                  r = new RoundaboutMon();
                  break;

               case 3:
                  r = new RoundaboutJava();
                  break;
            }
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }

      int num_users = 0;
      boolean flag = false;

      while(!flag)
      {
         System.out.println("Choose number of users in the system (Vehicles arriving to the roundabout)");

         try
         {
            num_users = Integer.parseInt(input.nextLine());

            if(num_users>0)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }

      for(int i=0; i<num_users; i++)
      {
         Util.rsleep(200, 1000);

         new Vehicle(r, i, Util.randVal(0,3)).start();
      }
   }
}
