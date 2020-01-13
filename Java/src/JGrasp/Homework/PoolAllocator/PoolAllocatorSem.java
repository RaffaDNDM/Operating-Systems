/**
   @author Di Nardo Di Maio Raffaele 1204879   

   ----------------------------------------------------------
                          Traccia 1
   ----------------------------------------------------------
        Implementazione del Pool Allocator con Semafori
   ----------------------------------------------------------
*/

package PoolAllocator;

import os.Semaphore;
import os.Timeout;

public class PoolAllocatorSem extends PoolAllocator
{
   //mutex
   private Semaphore m = new Semaphore(true);
   //Matrix of semaphores
   //(first index = num of R1 residual requests, second index = num of R2 residual requests)
   private Semaphore[][] alloc;
   
   /**
      Creation of the pool of resources
      @param num1 number of R1 resources in the pool
      @param num2 number of R2 resources in the pool
   */
   public PoolAllocatorSem(int num1, int num2)
   {
      super(num1, num2);
      
      //alloc[0][0] will not be used
      alloc = new Semaphore[num1+1][num2+1];
      
      for(int i=0; i<=num1; i++)
      {
         for(int j=0; j<=num2; j++)
         {
            alloc[i][j] = new Semaphore(false);
         }
      }  
   }
   
   public void register(int max1, int max2)
   {
      m.p();
      
      //Verify if the request was already registered   
      Request r = requests.get(Thread.currentThread());
      
      //the request was not defined
      if(r==null)
      {
         r = new Request(max1, max2);
         requests.put(Thread.currentThread(), r);
      }
      else  //the request was previously defined
      {
         if(r.get1()>0 || r.get2()>0)
            throw new NotAllowedException("Registration phase");
      }
      
      m.v();
   }

   public boolean alloc(int req1, int req2)
   {
      m.p();
   
      //Search the request in the set of requests (if the process has already done it)
      Request r = requests.get(Thread.currentThread());
      boolean allocated = false;
      printState();
      
      //If the process has already done a request
      if(r!=null)
      {
         //if the allocation would not respect the initial registration  
         if((req1+r.get1())>r.getMax1() || (req2+r.get2())>r.getMax2())
            throw new NotAllowedException("Not sospensive allocation phase");
         
         //if respect Banker's algorithm   
         if((r.getMax1()-r.get1())<=num1 && (r.getMax2()-r.get2())<=num2)
         {
            //update of available resources in the system
            num1-=req1;
            num2-=req2;
            r.allocate(req1, req2);
            allocated = true;
            printState();
         }
         //else allocation is not possible considering Banker's algorithm            
      }
      //else done by initialization of variable allocated 

      m.v();
      return allocated;      
   }

   public boolean alloc(int req1, int req2, long timeout)
   {
      m.p();
   
      //Search the request in the set of requests (if the process has already done it)
      Request r = requests.get(Thread.currentThread());
      
      //If the process has already done a request
      if(r!=null)
      {
         //if the allocation would not respect the initial registration  
         if((req1+r.get1())>r.getMax1() || (req2+r.get2())>r.getMax2())
            throw new NotAllowedException("Sospensive allocation phase");
         
         long remaining_time = 1;
         
         /**
            Waiting condition
            looking to Banker's algorithm conditions
         */
         if((r.getMax1()-r.get1())>num1 || (r.getMax2()-r.get2())>num2)
         {
            printState();
            m.v();
            remaining_time = alloc[r.getMax1()-r.get1()][r.getMax2()-r.get2()].p(timeout);
               
            //reached waiting time
            if(remaining_time == Timeout.EXPIRED)
            {
               m.v();
               return false;
            }
         }

         //Allocation phase
         num1-=req1;
         num2-=req2;
         r.allocate(req1, req2);
         printState();

         //else allocation is not possible considering Banker's algorithm            
      }
      //else done by initialization of variable allocated 

      m.v();
      return true;      
   }

   public void release()
   {  
      m.p();
      
      Request r;
      
      //If the process hasn't done a request
      if((r = requests.get(Thread.currentThread()))==null)
         throw new NotAllowedException("Release phase");      
      //If the process has already done a request
      else
      {
         //Release of the resources
         num1+=r.get1();
         num2+=r.get2();
         //Complete the request, removing it from the set of requests
         requests.remove(Thread.currentThread());   
         served++;
         printState();      
      
         //Wake up a process that is waiting 
         int[] indices = whichArise(num1, num2);
      
         if(indices[0]>0 || indices[1]>0)
            alloc[indices[0]][indices[1]].v();
      }
      
      m.v();
   }

   /**
      Search the first pendent request to wake up
      @param num1 quantity of resources R1 in this time
      @param num2 quantity of resources R2 in this time
      @return int[] indices (i,j) of pendent request (i<=num1, j<=num2)
   */
   public int[] whichArise(int num1, int num2)
   {
      int[] indices = {0, 0};   
   
      outer_loop:
      for(int i = num1; i>=0 ; i--)
      {
         for(int j = num2; j>=0; j--)
         {
            if(alloc[i][j].queue()>0)
            {
               indices[0]=i;
               indices[1]=j;
               
               break outer_loop;
            }     
         }
      }
      
      return indices;
   }
}