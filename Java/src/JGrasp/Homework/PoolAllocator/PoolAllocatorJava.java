/**
   @author Di Nardo Di Maio Raffaele 1204879   

   ----------------------------------------------------------
                          Traccia 3
   ----------------------------------------------------------
     Implementazione del Pool Allocator con Monitor di Java
   ----------------------------------------------------------
*/

package PoolAllocator;

public class PoolAllocatorJava extends PoolAllocator
{
   /**
      Creation of the pool of resources
      @param num1 number of R1 resources in the pool
      @param num2 number of R2 resources in the pool
   */
   public PoolAllocatorJava(int num1, int num2)
   {
      super(num1, num2);
   }

   public synchronized void register(int max1, int max2)
   {
      //Verify if the request was already registered   
      Request r = requests.get(Thread.currentThread());
      
      //the request was not defined
      if(r==null)
      {
         r = new Request(max1, max2);
         requests.put(Thread.currentThread(), r);      //indexOf(Object o)
      }
      else  //the request was previously defined
      {
         if(r.get1()>0 || r.get2()>0)
            throw new NotAllowedException("Registration phase");
      }    
   }

   public synchronized boolean alloc(int req1, int req2)
   {
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
         else if((r.getMax1()-r.get1())<=num1 && (r.getMax2()-r.get2())<=num2)
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

      return allocated;      
   }

   public synchronized boolean alloc(int req1, int req2, long timeout)
   {
      //Search the request in the set of requests (if the process has already done it)
      Request r = requests.get(Thread.currentThread());
      
      //If the process has already done a request
      if(r!=null)
      {
         //if the allocation would not respect the initial registration  
         if((req1+r.get1())>r.getMax1() || (req2+r.get2())>r.getMax2())
            throw new NotAllowedException("Sospensive allocation phase");
         
         long initial_time = System.currentTimeMillis();
         
         /**
            Waiting condition
            looking to Banker's algorithm conditions
         */
         while((r.getMax1()-r.get1())>num1 || (r.getMax2()-r.get2())>num2)
         {
            printState();
            
            try
            {
               wait(timeout);
            }
            catch(InterruptedException e)
            {
               System.out.println("Interrupted");
            }
            
            long passed_time = System.currentTimeMillis()-initial_time;
               
            //reached waiting time
            if(passed_time >= timeout)
            {
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

      return true;
   }

   public synchronized void release()
   {
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
      
         notifyAll();
      }     
   }
}