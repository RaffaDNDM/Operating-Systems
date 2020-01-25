/**
@author Di Nardo Di Maio Raffaele
*/

package Roundabout;

public class RoundaboutJava extends Roundabout
{
   private int[] ticket_sector = new int[4];
   private int[] service_sector = new int[4];

   public RoundaboutJava()
   {
      super();

      for(int i=0; i<SECTORS; i++)
      {
         ticket_sector[i] = 0;
         service_sector[i] = 0;
      }
   }

   public synchronized void enter(int sector)
   {
      int ticket = ticket_sector[sector]++;
      enqueued_cars[sector]++;

      printState();

      while(ticket!=service_sector[sector] || occupied[sector] || occupied[prev(sector)])
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

      service_sector[sector]++;
      enqueued_cars[sector]--;
      printState();

      occupied[sector]=true;
   }

   public synchronized int next(int sector)
   {
      int new_sector = succ(sector);

      printState();

      while(occupied[new_sector])
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

      occupied[sector] = false;
      occupied[new_sector] = true;
      printState();

      notifyAll();

      return new_sector;
   }

   public synchronized void exit(int sector)
   {
      occupied[sector] = false;
      served_cars++;
      printState();

      notifyAll();
   }
}
