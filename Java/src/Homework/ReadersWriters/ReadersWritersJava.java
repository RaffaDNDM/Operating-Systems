/**
   @author Di Nardo Di Maio Raffaele

   ------------------------------------------------------------
                         Traccia 14
   ------------------------------------------------------------
     Implementazione del Gestore di lock con Monitor di Java
   ------------------------------------------------------------
*/

package ReadersWriters;

public class ReadersWritersJava extends ReadersWriters
{
   //next ticket for writers
   private int ticket_exclusive = 0;
   //next writer that is going to be served
   private int service_exclusive = 0;
   //next ticket for readers
   private int ticket_shared = 0;
   //next reader that is going to be served
   private int service_shared = 0;

   public synchronized void exclusive_lock()
   {
      //Assignment of ticket to the writer
      int ticket = ticket_exclusive++;
      //increase the number of writers waiting for their turn
      enqueued_exclusive++;
      print_state();

      /*
         Waiting condition
         -> the writer isn't the first element of the queue
         -> there is another writer in service
      */
      while(ticket!=service_exclusive || !exclusive)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interrupted");
         }
      }

      //increase the service ticket for writers
      service_exclusive++;
      //decrease the number of writers waiting for their turn
      enqueued_exclusive--;
      //keep the mark shared with readers
      exclusive = false;
      print_state();

      /*
         Waiting condition
         there are other readers that are reading
      */
      while(num_shared>0)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interrupted");
         }
      }

      print_state();
   }

   public synchronized void shared_lock()
   {
      //Assignment of ticket to the reader
      int ticket = ticket_shared++;
      //Increase the number of readers waiting for their turn
      enqueued_shared++;
      print_state();

      /*
         Waiting condition
         -> the reader isn't the first element of the queue
         -> there is a writer
         -> there are other waiting writers
      */
      while(ticket!=service_shared || !exclusive || enqueued_exclusive>0)
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interrupted");
         }
      }


      //increase the service ticket for readers
      service_shared++;
      //decrease the number of readers waiting for their turn
      enqueued_shared--;
      //increase the number of readers that are reading
      num_shared++;
      print_state();
   }

   public synchronized void exclusive_unlock()
   {
      //release the mark shared with writers
      exclusive = true;
      //increase the number of served writers
      served_exclusive++;
      print_state();

      //wake up other users
      notifyAll();
   }

   public synchronized void shared_unlock()
   {
      //decrease the number of readers that are reading
      num_shared--;
      //increase the number of served readers
      served_shared++;
      print_state();

      //wake up other users
      notifyAll();
   }
}
