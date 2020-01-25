/**
@author Di Nardo Di Maio Raffaele
*/

package Allocator12;

import java.util.Scanner;
import os.Util;

public class All12_Main
{
   public static void main(String[] args)
   {
      long min = Long.parseLong(args[0]);
      long max = Long.parseLong(args[1]);

      Scanner input = new Scanner(System.in);
      System.out.println("Insert the number of processes");
      int num_processes = Integer.parseInt(input.nextLine());

      System.out.println("Insert the number of resources available in the pool");
      int num = Integer.parseInt(input.nextLine());

      Allocator12 all = null;

      System.out.println("Select the method of synchronization");
      System.out.println("     Semaphore : 1");
      System.out.println(" Hoare Monitor : 2");
      System.out.println("        Region : 3");
      System.out.println("  Java Monitor : 4");

      int method = Integer.parseInt(input.nextLine()); //Method selected by the user

      switch(method)
      {
         //Semaphore
         case(1):
         {
            all = new All12_Semaphore(num, min, max);
            break;
         }

         //Monitor
         case(2):
         {
            all = new All12_Monitor(num, min, max);
            break;
         }

         //Region
         case(3):
         {
            all = new All12_Region(num, min, max);
            break;
         }

         //Java
         case(4):
         {
            all = new All12_Java(num, min, max);
            break;
         }
      }

      for(int i=0; i<num_processes; i++)
      {
         int type = Util.randVal(0,2);
         new Process(i, type, all).start();
         Util.rsleep(200,1000);
      }
   }
}
