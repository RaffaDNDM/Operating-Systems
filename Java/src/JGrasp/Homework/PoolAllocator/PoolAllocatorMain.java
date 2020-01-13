/**
   @author Di Nardo Di Maio Raffaele 1204879
*/

package PoolAllocator;

import java.util.Scanner;
import os.Util;

public class PoolAllocatorMain
{
   public static void main(String[] args)
   {
      Scanner input = new Scanner(System.in);
      
      boolean flag = false;
      int numR1 = 0;
      
      while(!flag)
      {
         System.out.println("Choose number of resources of type R1 in the pool");
         
         try
         {
            numR1 = Integer.parseInt(input.nextLine());
            
            if(numR1>=0)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }
      
      flag = false;
      int numR2 = 0;
      
      while(!flag)
      {
         System.out.println("Choose number of resources of type R2 in the pool");
         
         try
         {
            numR2 = Integer.parseInt(input.nextLine());
            
            if(numR2>=0)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }
      
      PoolAllocator pa = null;
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
                  pa = new PoolAllocatorSem(numR1, numR2);
                  break;        
                          
               case 2:
                  pa = new PoolAllocatorMon(numR1, numR2);
                  break;
               
               case 3:
                  pa = new PoolAllocatorJava(numR1, numR2);
                  break;
            }
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }
      
      int num_users = 0;
      flag = false;
      
      while(!flag)
      {
         System.out.println("Choose number of processes");
         
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
      
      flag = false;
      long min_time = 0;
      
      while(!flag)
      {
         System.out.println("Choose MIN TIME between the arrival of 2 users");
         
         try
         {
            min_time = Long.parseLong(input.nextLine());
            
            if(min_time>=0)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }
      
      flag = false;
      long max_time = 0;
      
      while(!flag)
      {
         System.out.println("Choose MAX TIME between the arrival of 2 users");
         
         try
         {
            max_time = Long.parseLong(input.nextLine());
            
            if(max_time>min_time)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }
      
      for(int i=0; i<num_users; i++)
      {
         new Process(i, pa).start();
         Util.rsleep(min_time, max_time);
      }
   }
}