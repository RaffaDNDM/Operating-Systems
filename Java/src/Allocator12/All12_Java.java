/**
@author Di Nardo Di Maio Raffaele
*/

package Allocator12;

public class All12_Java extends Allocator12
{
   public All12_Java(int num, long min, long max)
   {
      init_state(num, min, max);
   }

   public synchronized void req2_1()
   {
      while(num<2)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interruption");
         }
      }

      num--;
      printState();
   }

   public synchronized void req2_all()
   {
      while(num<2)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interruption");
         }
      }

      num-=2;
      printState();
   }

   public synchronized void req1()
   {
      while(num<1)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interruption");
         }
      }

      num--;
      printState();
   }

   public synchronized void end(int num_res)
   {
      num+=num_res;
      count++;
      printState();
      printCount();

      notifyAll();
   }
}
