/**
   @author Di Nardo Di Maio Raffaele

   Test class
*/

package MultiplePC;

import java.util.Scanner;
import os.Util;

public class MultiplePCMain
{
   public static void main(String[] args)
   {
      MultiplePC mpc = null;
      Scanner input = new Scanner(System.in);

      int size = 0;
      boolean flag = false;

      //Choice of the size of the buffer
      while(!flag)
      {
         System.out.println("Choose size of buffer");

         try
         {
            size = Integer.parseInt(input.nextLine());

            if(size>0)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }

      int type = 0;

      //Choice of the type of synchronization
      while(type<1 || type>2)
      {
         System.out.println("Choose method of synchronization:");
         System.out.println("1. Private Semaphore");
         System.out.println("2. Hoare's Monitor");

         try
         {
            type = Integer.parseInt(input.nextLine());

            switch(type)
            {
               case 1:
                  mpc = new MultiplePCSem(size);
                  break;

               case 2:
                  mpc = new MultiplePCMon(size);
                  break;
            }
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }

      int num_producers = 0;
      flag = false;

      //Choice of number of producers
      while(!flag)
      {
         System.out.println("Choose number of producers in the system");

         try
         {
            num_producers = Integer.parseInt(input.nextLine());

            if(num_producers>0)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }

      int num_consumers = 0;
      flag = false;

      //Choice of number of consumers
      while(!flag)
      {
         System.out.println("Choose number of consumers in the system");

         try
         {
            num_consumers = Integer.parseInt(input.nextLine());

            if(num_consumers>0)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }

      int max = (num_consumers>=num_producers)? num_consumers : num_producers;

      for(int i=0; i<max; i++)
      {
         Util.rsleep(200, 1000);

         if(i<num_consumers)
            new Consumer(i, mpc).start();

         if(i<num_producers)
            new Producer(i, mpc).start();
      }
   }
}
