/**
@author Di Nardo Di Maio Raffaele
*/

package ProducerConsumer;

import os.Util;
import java.util.Scanner;


/**Testing class*/
public class PC_Main
{
   /**
      main method
   */
   public static void main(String[] args)
   {
      if(args.length<=0)
      {
         System.out.println("No double specification of random sleep in input (min max)");
         return;
      }

      //min time of service for a producer/consumer
      long min = Long.parseLong(args[0]);
      //max time of service for a producer/consumer
      long max = Long.parseLong(args[1]);

      ProducerConsumer pc = null;

      //Select the synchronization method at Runtime
      System.out.println("Select the method of synchronization");
      System.out.println("     Semaphore : 1");
      System.out.println(" Hoare Monitor : 2");
      System.out.println("        Region : 3");
      System.out.println("  Java Monitor : 4");

      Scanner input = new Scanner(System.in);
      int method = Integer.parseInt(input.nextLine()); //Method selected by the user

      /** The user chooses if he wants that a producer can write while a consumer reads */
      String parallel = "";
      while(parallel.compareTo("y")!=0 && parallel.compareTo("n")!=0 && (method==1 || method==2))
      {
         System.out.println("Do you want to allow parallel readings and writings? (y/n)");
         parallel = input.nextLine().toLowerCase();
      }

      switch(method)
      {
         //Semaphore
         case(1):
         {
            if(parallel.compareTo("y")==0)
               pc = new PC_Semaphore_Parallel();
            else
               pc = new PC_Semaphore();

            break;
         }

         //Monitor
         case(2):
         {
            if(parallel.compareTo("y")==0)
               pc = new PC_Monitor_Parallel();
            else
               pc = new PC_Monitor();

            break;
         }

         //Region
         case(3):
         {
            pc = new PC_Region();
            break;
         }

         //Java
         case(4):
         {
            pc = new PC_Java();
            break;
         }
      }

      System.out.println("Insert the number of producers:");
      int num_producers = Integer.parseInt(input.nextLine());

      System.out.println("Insert the number of consumers:");
      int num_consumers = Integer.parseInt(input.nextLine());

      for(int i=0; i<num_producers; i++)
      {
         new Producer(i+1, pc, min, max).start();
      }

      for(int i=0; i<num_consumers; i++)
      {
         new Consumer(i+1, pc, min, max).start();
      }
   }
}
