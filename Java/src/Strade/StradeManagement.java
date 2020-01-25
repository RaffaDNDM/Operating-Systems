/**
@author Di Nardo Di Maio Raffaele
*/

package Strade;

import os.Util;
import java.util.Scanner;

public class StradeManagement
{
   public static void main(String[] args)
   {
      Strade s = null;

      Scanner input = new Scanner(System.in);
      int type = 0;

      //Choice of the type of synchronization
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
                  s = new StradeSem();
                  break;

               case 2:
                  s = new StradeMon();
                  break;

               case 3:
                  s = new StradeJava();
                  break;
            }
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }

      for(int i=0; i<20; i++)
      {
         new Veicolo(i,s).start();
         Util.rsleep(100,500);
      }
   }
}
