/**
   @author Di Nardo Di Maio Raffaele
*/

package PoolAllocator;

import os.Util;

public class Process extends Thread
{
   //Number of already allocated resources R1
   private int doneR1 = 0;
   //Number of already allocated resources R2
   private int doneR2 = 0;
   //Number of max declared resources R1
   private int maxR1 = -1;
   //Number of max declared resources R2
   private int maxR2 = -1;
   //identifier of the thread
   private int id;
   //PoolAllocator instance
   private PoolAllocator pa;

   /**
      Process that does a request of resources
      @param id identifier of the thread
      @param pa PoolAllocator instance
   */
   public Process(int id, PoolAllocator pa)
   {
      this.pa = pa;
      this.id = id;
   }

   public void run()
   {
      //Definition of max number of resources R1 and R2
      //looking to the max possible number of resources
      int maxR1 = Util.randVal(1, pa.num_R1());
      int maxR2 = Util.randVal(1, pa.num_R2());

      //Registration of max declarations of resources
      pa.register(maxR1, maxR2);

      //Allocation phase
      while((maxR1-doneR1)!=0 || (maxR2-doneR2) != 0)
      {
         //new requests based on residual requests that aren't yet satisfied
         int req1 = ((maxR1-doneR1)<=3)? (maxR1-doneR1) : Util.randVal(1, (maxR1-doneR1));
         int req2 = ((maxR2-doneR2)<=3)? (maxR2-doneR2) : Util.randVal(1, (maxR2-doneR2));
         boolean with_timeout = (Util.randVal(1, 10)<4)? true: false;

         boolean allocated = with_timeout? pa.alloc(req1, req2, Util.randVal(1000L, 3000L)) : pa.alloc(req1, req2);

         //Update allocated resources counters
         if(allocated)
            pa.query();

         //Simulation of reading
         Util.rsleep(2000L, 4000L);
      }

      pa.release();
   }

   public void setDoneR1(int doneR1)
   {
      this.doneR1 = doneR1;
   }

   public void setDoneR2(int doneR2)
   {
      this.doneR2 = doneR2;
   }
}
