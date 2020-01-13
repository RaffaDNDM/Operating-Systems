/**
   @author Raffaele Di Nardo Di Maio 1204879
   
   --------------------------------------------------------------------------
                                    TRACCIA 6
   --------------------------------------------------------------------------
      Modellare l'attesa ad uno sportello unico con il Monitor di Java
      ma con una attesa ordinata. A tale scopo si modelli la prenotazione
      con prelievo di un ticket numerato progressivo (si pensi a talune
      attuali sale d'attesa di uffici pubblici). Prevedere anche un ticket
      urgente che ha priorità su quello normale, assumendo che i clienti
      urgenti siano sporadici. Il servizio allo sportello venga modellato con
      un'opportuna attesa casuale. Prevedere anche brevi intervalli di tempo
      casuali durante i quali lo sportello è fuori servizio, nonché la
      possibilità che il cliente precisi un timeout (massima attesa in coda).
   --------------------------------------------------------------------------
*/

package OneDesk;
import os.Util;
import java.util.Scanner;
import java.util.ArrayList;

public class OneDesk
{
   //Total number of clients
   private final static int NUM_CLIENTS = 30;

   //Max time between an out of service and the next one
   private final static long MAX_TURN = 12000;
   //Min interval of time in which the desk is out of service
   private final static long MIN_NO_SERVICE = 3000;
   //Max interval of time in which the desk is out of service
   private final static long MAX_NO_SERVICE = 4000;

   //state of the desk (true=free, false=occupied)
   boolean free = true;
   //availability of the desk (true=not available, false=available)
   boolean pause = false;
   
   //Ticket for priority clients
   int ticket_P = 0; 
   //Counter of enqueued priority clients
   int enqueued_P = 0;
   //Counter of served priority clients
   int served_P = 0;
   //Ticket for normal clients
   int ticket_N = 0;
   //Counter of enqueued normal clients
   int enqueued_N = 0;
   //Counter of served normal clients
   int served_N = 0;
   //Counter of clients that exit because they were tired
   int exit_count = 0; 
   //Set of normal tickets that are going to be served (in order of service)
   private ArrayList<Integer> tickets_P = new ArrayList<>();
   //Set of priority tickets that are going to be served (in order of service)
   private ArrayList<Integer> tickets_N = new ArrayList<>();
   
   /**
      Method used by a client to enter in the system
      @param priority true if the client has priority
      @param timeout max waiting time of a client in the queue
      @return amount of remaining time w.r.t. timeout
   */
   public synchronized long enter(boolean priority, long timeout)
   {
      int ticket;
      
      long begin = System.currentTimeMillis();
      long end =0;
      
      if(priority) //the client has priority
      {
         /*
            Assigns a ticket to the client, enqueuing him to the 
            input queue of priority clients
            The ticket is added to the set of future tickets
         */
         ticket = ticket_P++;
         tickets_P.add(ticket);
         enqueued_P++;
      
         System.out.printf("Enqueued_P: %2d Enqueued_N: %2d \n",enqueued_P, enqueued_N);
         
         //Is the turn of the client?
         while(ticket!=tickets_P.get(0) || !free || end==0 || pause)
         {
            try
            {
               //the client needs to wait because it's not its turn
               wait(timeout);

               end = System.currentTimeMillis();
               
               //the client is tired and exits from the system
               if((end-begin)>=timeout)
               {
                  //the client exit from the queue
                  enqueued_P--;
                  exit_count++; 
                  
                  /*
                     I remove the ticket of this client from the set of tickets
                     that will be served in the future
                  */
                  tickets_P.remove((Object) ticket);
                  
                  /*
                     I notify the update of the shared knowledge to other users
                  */
                  notifyAll();
                  return 0;
               }
            }
            catch(InterruptedException e)
            {
               System.out.println("Interrupted");
            }                 
         }
         
         /*
            I remove the ticket used in this case by the client that 
            began the service
         */
         tickets_P.remove(0);
         enqueued_P--;
      }
      else //the client has not priority
      {
         /*
            Assigns a ticket to the client, enqueuing him to the 
            input queue of normal clients
            The ticket is added to the set of future tickets
         */
         ticket = ticket_N++;
         enqueued_N++;
         tickets_N.add(ticket);
      
         System.out.printf("Enqueued_P: %2d Enqueued_N: %2d \n",enqueued_P, enqueued_N);
      
         //Is the turn of the client?
         while(ticket!=tickets_N.get(0) || !free || enqueued_P>0|| end==0 || pause)
         {
            try
            {
               //the client needs to wait because it's not its turn
               wait(timeout);
               
               end = System.currentTimeMillis();
               
               //the client is tired and exits from the system               
               if((end-begin)>=timeout)
               {
                  //the client exit from the queue
                  enqueued_N--;
                  exit_count++;
                  
                  /*
                     I remove the ticket of this client from the set of tickets
                     that will be served in the future
                  */
                  tickets_N.remove((Object) ticket);
                  
                  /*
                     I notify the update of the shared knowledge to other users
                  */
                  notifyAll();
                  return 0;
               }
            }
            catch(InterruptedException e)
            {
               System.out.println("Interrupted");
            }         
         }
         
         /*
            I remove the ticket used in this case by the client that 
            began the service
         */
         tickets_N.remove(0);
         enqueued_N--;
      }
      
      //occupied desk by the client
      free=false;
      
      return end-begin;
   }

   /**
      Method used used by a client to enter in the system
      without the possibility of timeout specification
      @param priority true if the client has priority
   */
   public synchronized void enter(boolean priority)
   {
      int ticket;
   
      if(priority) //the client has priority
      {
         /*
            Assigns a ticket to the client, enqueuing him to the 
            input queue of priority clients
            The ticket is added to the corresponding set of future tickets
         */         
         ticket = ticket_P++;
         tickets_P.add(ticket);
         enqueued_P++;
         
         System.out.printf("Enqueued_P: %2d Enqueued_N: %2d \n",enqueued_P, enqueued_N);
         
         //Is the turn of the client?      
         while(ticket!=tickets_P.get(0) || !free || pause)
         {
            try
            {
               //the client needs to wait because it's not its turn
               wait();
            }
            catch(InterruptedException e)
            {
               System.out.println("Interrupted");
            }                 
         }
         
         /*
            I remove the ticket used in this case by the client that 
            began the service
         */
         tickets_P.remove(0);
         enqueued_P--;
      }
      else //the client has priority
      {
         /*
            Assigns a ticket to the client, enqueuing him to the 
            input queue of normal clients
            The ticket is added to the set of future tickets
         */
         ticket = ticket_N++;
         tickets_N.add(ticket);
         enqueued_N++;
      
         System.out.printf("Enqueued_P: %2d Enqueued_N: %2d \n",enqueued_P, enqueued_N);   
      
         //Is the turn of the client?
         while(ticket!=tickets_N.get(0) || !free || pause || enqueued_P>0)
         {
            try
            {
               //the client needs to wait because it's not its turn            
               wait();
            }
            catch(InterruptedException e)
            {
               System.out.println("Interrupted");
            }         
         }
         
         /*
            I remove the ticket used in this case by the client that 
            began the service
         */
         tickets_N.remove(0);
         enqueued_N--;
      }
      
      //occupied desk by the client
      free=false;
   }
   
   /**
      Method used by a client to exit from the system at the end of the service
      @param priority true if the client has priority
   */
   public synchronized void exit(boolean priority)
   {
      //free desk
      free = true;
      
      //update of number of served clients
      if(priority)
         served_P++;
      else
         served_N++;
         
      System.out.printf("Served_P: %2d Served_N: %2d Out: %2d\n",served_P, served_N, exit_count);
      
      //wakes up waiting clients
      notifyAll();
   }
   
   private class Client extends Thread
   {
      //id that identifies the thread (client)
      private int id;
      //true if the client has priority
      private boolean priority;
      //timeout if specified for the user
      private long timeout=0;
      //service time
      private final static long MIN_SERVICE = 2000L;
      private final static long MAX_SERVICE = 4000L;
   
      /**
         Client in the system
         @param id number that identifies the client
         @param priority true if the client has priority
      */
      public Client(int id, boolean priority)
      {
         this.id = id;
         this.priority = priority;
      }
      
      /**
         Client in the system
         @param id number that identifies the client
         @param priority true if the client has priority
         @param timeout max waiting time for the client, before he exits from the system
      */
      public Client(int id, boolean priority, long timeout)
      {
         this.id = id;
         this.priority = priority;
         this.timeout = timeout;
      }
   
      public void run()
      {
         if(timeout==0) //client without a timeout
            enter(priority);
         else //client with a timeout
         {
            long time = enter(priority, timeout);
            
            if(time==0) //the client has reach its timeout and exits from the system
            {
               System.out.println("The client "+id+" doesn't want to wait anymore and exits from the system");
               return;
            }
         }
         
         //Simulation of service
         Util.rsleep(MIN_SERVICE, MAX_SERVICE);
         //Service Completed
         exit(priority);
      }
   }

   /**
      Method used by background task that manages the turns of employees
   */
   public void no_service()
   {
      while(true)
      {
         //Random time between 2 consecutive pauses of the desk employee
         Util.rsleep(0, MAX_TURN);
         
         synchronized(this)
         {
            pause = true;
            
            try
            {
               //The task is waiting that no client is doing its service at the desk
               wait();
               
               //not available desk
               free = false;
            }
            catch(InterruptedException e)
            {
               System.out.println("Interrupted");
            }
         }
         
         //Time in which the service at the desk is not available
         System.out.println("The employer isn't at the desk");
         Util.rsleep(MIN_NO_SERVICE, MAX_NO_SERVICE);
         System.out.println("The employer has come back");
         
         synchronized(this)
         { 
            //The task makes the desk available and wakes up waiting clients
            pause=false;
            free = true;
            notifyAll();
         }
      }
   }

   private class Turn extends Thread
   {
      /**
         Background task that manages the availability of the desk   
      */
      public Turn()
      {}
      
      public void run()
      {
         //do the same operation in an infinite loop
         no_service();
      }
   }

   public static void main(String[] args)
   {      
      long max_timeout = 0;
      
      //Management of the client timeout given in input
      while(max_timeout<=0)
      {   
         System.out.println("Insert the max timeout in ms (integer > 0) for a client:");
         Scanner input = new Scanner(System.in);
         max_timeout = Long.parseLong(input.nextLine());
      }
   
      //Creation of the system
      OneDesk system = new OneDesk();
   
      for(int i=0; i<NUM_CLIENTS; i++)
      {
         //value used to define if the client has the priority
         int type = Util.randVal(0, 100);  
         //value used to define if the client has timeout
         int time_choice = Util.randVal(0, 100);
         
         //priority for the i-th client with p=0.3
         boolean priority = type<30;
         
         if(time_choice<40) //client with timeout with p=0.4
            system.new Client(i, type<30, Util.randVal(1000, max_timeout)).start();
         else //client without timeout with p=0.6
            system.new Client(i, type<30).start();
        
         //random time of the arrival of clients
         Util.rsleep(400, 100);
      }
      
      //activation of the management of availability of the desk
      system.new Turn().start();
   }
}
