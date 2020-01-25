/**
@author Di Nardo Di Maio Raffaele
*/

package Allocator12;

import os.Semaphore;

public class All12_Semaphore extends Allocator12
{
   private Semaphore mutex = new Semaphore(true);
   private Semaphore requested_1 = new Semaphore(false);
   private Semaphore requested_2 = new Semaphore(false);

   public All12_Semaphore(int num, long min, long max)
   {
      init_state(num, min, max);
   }

   public void req2_1()
   {
      mutex.p();

      if(num<2)
      {
         mutex.v();
         requested_2.p();
      }

      num--;

      mutex.v();
   }

   public void req2_all()
   {
      mutex.p();

      if(num<2)
      {
         mutex.v();
         requested_2.p();
      }

      num-=2;

      printState();

      mutex.v();
   }

   public void req1()
   {
      mutex.p();

      if(num<1)
      {
         mutex.v();
         requested_1.p();
      }

      num--;

      printState();

      mutex.v();
   }

   public void end(int num_res)
   {
      mutex.p();
      num+=num_res;
      count++;

      printState();
      printCount();

      if(num_res==2)
      {
         if(requested_2.queue()>0)
            requested_2.v();
         else if(requested_1.queue()>0)
            requested_1.v();
      }
      else if(num_res==1 && requested_1.queue()>0)
         requested_1.v();

      mutex.v();
   }
}
