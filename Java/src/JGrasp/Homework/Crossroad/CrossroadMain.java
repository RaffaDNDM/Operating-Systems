/**
   @author Di Nardo Di Maio Raffaele 1204879
*/

package Crossroad;

import java.util.Scanner;
import os.Util;

public class CrossroadMain
{
   public static void main(String[] args)
   {
      Scanner input = new Scanner(System.in);

      //Choice of the number of cars
      int num_cars = 0;
      boolean flag = false;
      
      while(!flag)
      {
         System.out.println("Choose the number of cars");
         
         try
         {
            num_cars = Integer.parseInt(input.nextLine());
            
            if(num_cars>0)
               flag = true;
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
      }
      

      //Choice of the type of synchronization method
      Crossroad cr = null;
      flag = false;
      int type = 0;

      while(type<1 || type>3)
      {
   		System.out.println("Choose the type of synchronization method");
   		System.out.println("1. Hoare Monitor");
   		System.out.println("2. Java Monitor");
   		System.out.println("3. Semaphore");
   		
         try
         {
            type = Integer.parseInt(input.nextLine());
         
      		switch(type)
      		{
      			case 1:
      				cr = new CrossroadMon();
      				break;
                  
      			case 2:
      				cr = new CrossroadJava();
      				break;
                  
      			case 3:
      				cr = new CrossroadSem();
      				break;
      		}
         }
         catch(NumberFormatException e)
         {
            System.out.println("It's not an integer");
         }
		}
      
      
      //Simulation of arrival of the cars
      for (int i = 0; i < num_cars; i++)   
      {
         new Car(i, cr).start();
         Util.rsleep(250, 1000);
      }
   }
}