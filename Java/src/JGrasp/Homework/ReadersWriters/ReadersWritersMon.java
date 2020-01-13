/**
   @author Di Nardo Di Maio Raffaele 1204879
   
   -------------------------------------------------------------
                         Traccia 13
   -------------------------------------------------------------
      Implementazione del Gestore di lock con Monitor di Hoare
   -------------------------------------------------------------
*/

package ReadersWriters;

import os.Monitor;
import os.Monitor.Condition;

public class ReadersWritersMon extends ReadersWriters
{
   // Monitor
   Monitor m = new Monitor();
   
   //Queue of threads waiting for exclusive lock 
   Condition queueE = m.new Condition();
   //Queue of threads waiting for shared lock
   Condition queueS = m.new Condition();   
   //Place for the thread waiting for release of all shared locks
   Condition endS = m.new Condition();

   public void exclusive_lock()
   {
      m.mEnter();
   
      //increase the number of writers waiting for their turn
      enqueued_exclusive++;
      print_state();
      
      /*
         Waiting condition
         there is another writer in service
      */
      if(!exclusive)
         queueE.cWait();
         
      //decrease the number of writers waiting for their turn
      enqueued_exclusive--;
      exclusive = false;
      print_state();
      
      /*
         Waiting condition
         there are other readers that are reading
      */      
      if(num_shared>0)
         endS.cWait();
   
      m.mExit();
   }

   public void shared_lock()
   {
      m.mEnter();
   
      //Increase the number of readers waiting for their turn
      enqueued_shared++;
      print_state();
      
      /*
         Waiting condition
         -> there is a writer
         -> there are other waiting writers
      */
      if(!exclusive || enqueued_exclusive>0)
         queueS.cWait();
         
      //decrease the number of readers waiting for their turn
      enqueued_shared--;
      //increase the number of readers that are reading
      num_shared++;
      print_state();
   
      m.mExit();
   }
   
   public void exclusive_unlock()
   {
      m.mEnter();
   
      //release the mark shared with writers
      exclusive = true;
      //increase the number of served writers
      served_exclusive++;
      print_state();
      
      if(enqueued_exclusive>0)
         queueE.cSignal();
      else if (enqueued_shared>0)
         queueS.cSignal();   
   
      m.mExit();   
   }
   
   public void shared_unlock()
   {
      m.mEnter();
      
      //decrease the number of readers that are reading
      num_shared--;
      //increase the number of served readers      
      served_shared++;
      print_state();
      
      if(!exclusive && num_shared==0)
         endS.cSignal();
      else if(exclusive && enqueued_exclusive>0)
         queueE.cSignal();
      else if (exclusive && enqueued_shared>0 && enqueued_exclusive==0)
         queueS.cSignal();   
     
      m.mExit();   
   }
}