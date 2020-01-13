/**
   @author Di Nardo Di Maio Raffaele 1204879
   
   -----------------------------------------------------
                      Traccia 12
   -----------------------------------------------------
     Implementazione del Gestore di lock con Semafori
   -----------------------------------------------------
*/

package ReadersWriters;

import os.Semaphore;

public class ReadersWritersSem extends ReadersWriters
{
   // Semaphore
   Semaphore m = new Semaphore(true);
   
   //Queue of threads waiting for exclusive lock 
   Semaphore queueE = new Semaphore(false);
   //Queue of threads waiting for shared lock
   Semaphore queueS = new Semaphore(false);
   //Place for the thread waiting for release of all shared locks
   Semaphore endS = new Semaphore(false);

   public void exclusive_lock()
   {
      m.p();
      
      //increase the number of writers waiting for their turn
      enqueued_exclusive++;
      print_state();
      
      /*
         Waiting condition
         there is another writer in service
      */
      if(!exclusive)
      {
         m.v();
         queueE.p();
      }  
      
      //decrease the number of writers waiting for their turn
      enqueued_exclusive--;
      exclusive = false;
      print_state();
      
      /*
         Waiting condition
         there are other readers that are reading
      */
      if(num_shared>0)
      {
         m.v();
         endS.p();
      }
      
      m.v();
   }

   public void shared_lock()
   {
      m.p();
   
      //Increase the number of readers waiting for their turn
      enqueued_shared++;
      print_state();
      
      /*
         Waiting condition
         -> there is a writer
         -> there are other waiting writers
      */
      if(!exclusive || enqueued_exclusive>0)
      {
         m.v();
         queueS.p();
      }   
      
      //decrease the number of readers waiting for their turn
      enqueued_shared--;
      //increase the number of readers that are reading
      num_shared++;
      print_state();
   
      m.v();
   }
   
   public void exclusive_unlock()
   {
      m.p();
   
      //release the mark shared with writers
      exclusive = true;
      //increase the number of served writers
      served_exclusive++;
      print_state();
      
      if(enqueued_exclusive>0)
         queueE.v();
      else if (enqueued_shared>0)
         queueS.v();   
   
      m.v();   
   }
   
   public void shared_unlock()
   {
      m.p();
      
      //decrease the number of readers that are reading
      num_shared--;
      //increase the number of served readers      
      served_shared++;
      print_state();
      
      if(!exclusive && num_shared==0)
         endS.v();
      else if(exclusive && enqueued_exclusive>0)
         queueE.v();
      else if (exclusive && enqueued_shared>0 && enqueued_exclusive==0)
         queueS.v();   
     
      m.v();   
   }
}