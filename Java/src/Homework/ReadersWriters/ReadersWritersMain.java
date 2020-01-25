/**
   @author Di Nardo Di Maio Raffaele
*/

package ReadersWriters;

import os.Util;
import java.util.*;

public class ReadersWritersMain
{
   public static void main(String[] args)
   {
      ReadersWriters rw = null;
      Scanner input = new Scanner(System.in);

      int type = 0;

      //Choice of the method of synchronization
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
                  rw = new ReadersWritersSem();
                  break;

               case 2:
                  rw = new ReadersWritersMon();
                  break;

               case 3:
                  rw = new ReadersWritersJava();
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

      //Choice of the number of users in the system
      while(!flag)
      {
         System.out.println("Choose number of users");

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

      //Choice of MIN time and MAX time between two user arrivals
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
         int choice = Util.randVal(1,10);

         if(choice<4)
            new Writer(i, rw).start();
         else
            new Reader(i, rw).start();

         Util.rsleep(min_time, max_time);
      }
   }
}
