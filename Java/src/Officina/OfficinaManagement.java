/**
@author Di Nardo Di Maio Raffaele
*/

package Officina;

import os.Util;

import java.util.Scanner;

public class OfficinaManagement
{
   public static void main(String[] args)
   {
      Officina o = null;
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
                  o = new OfficinaSem(Integer.parseInt(args[0]));
                  break;

               case 2:
                  o = new OfficinaMon(Integer.parseInt(args[0]));
                  break;

               case 3:
                  o = new OfficinaJava(Integer.parseInt(args[0]));
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
         Util.rsleep(100, 500);
         new Bot(i, Util.randVal(0,1), 4000, 8000, o).start();
      }
   }
}
