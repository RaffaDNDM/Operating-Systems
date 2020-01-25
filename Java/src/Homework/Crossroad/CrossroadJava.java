/**
   @author Di Nardo Di Maio Raffaele  

   -----------------------------------------------------
                         Traccia 9
   -----------------------------------------------------
     Implementazione dell'incrocio con Monitor di Java
   -----------------------------------------------------
*/
package Crossroad;

public class CrossroadJava extends Crossroad
{
   //next ticket for cars from North
   private int ticket_NS=0;
   //next ticket for cars from East
   private int ticket_EW=0;
   //next car from North that is going to be served
   private int service_NS=0;
   //next car from East that is going to be served
   private int service_EW=0;

   public synchronized void enterNS()
   {
      //Assignment of ticket to the car
      int ticket = ticket_NS++;
      //increase number of cars from North waiting for their turn
      enqueued_NS++;
      print_state();

      /*
         Waiting condition
         -> no free places in the crossroad
         -> no turn of cars from North
         -> turn of cars from North but it reached max number of consecutive NS cars
      */
      while(ticket!=service_NS || capacity==0 || ((enqueued_EW>0 || in_service>0) && !isNS) || (isNS && on_dir==0))
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

      //dequeue the car from NS waiting queue
      enqueued_NS--;
      //occupy a free place in the crossroad
      capacity--;
      //increase number of cars crossing the crossroad in this moment
      in_service++;
      //update of service ticket
      service_NS++;

      /*
         update of number of possible consecutive NS cars for the future
         if and only if there are some waiting EW cars
      */
      on_dir = (enqueued_EW>0)? (on_dir-1): on_dir;
      print_state();
   }

   public synchronized void enterEW()
   {
      //Assignment of ticket to the car
      int ticket = ticket_EW++;
      //increase number of cars from East waiting for their turn
      enqueued_EW++;
      print_state();

      /*
         Waiting condition
         -> no free places in the crossroad
         -> no turn of cars from East
         -> turn of cars from East but it reached max number of consecutive EW cars
      */
      while(ticket != service_EW || capacity==0 || ((enqueued_NS>0 || in_service>0) && isNS) || (!isNS && on_dir==0))
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

      //dequeue the car from EW waiting queue
      enqueued_EW--;
      //occupy a free place in the crossroad
      capacity--;
      //increase number of cars crossing the crossroad in this moment
      in_service++;
      //update of service ticket
      service_EW++;

      /*
         update of number of possible consecutive EW cars for the future
         if and only if there are some waiting NS cars
      */
      on_dir = (enqueued_NS>0)? (on_dir-1): on_dir;
      print_state();
   }

   public synchronized void exit()
   {
      //increase the number of cars that crossed the crossroad
      served++;
      //increase the number of free places in the crossroad
      capacity++;
      //decrease the number of cars that are crossing the crossroad in this moment
      in_service--;
      print_state();

      if(in_service==0)
      {
         /*
            Update the turn of the cars if:
            -> the actual direction has reached the max number of consecutive cars
            -> it's the turn of the actual type of cars that crossed the crossroad
               but there are no more cars waiting on this direction
         */
         boolean end = on_dir==0 || (on_dir>0 && ((isNS && enqueued_NS==0) || (!isNS && enqueued_EW==0)));
         isNS = end ? !isNS : isNS;
         on_dir = MAX_ON_DIR;

         notifyAll();
      }
   }
}
