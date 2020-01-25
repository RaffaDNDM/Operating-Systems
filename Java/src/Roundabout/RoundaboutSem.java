/**
@author Di Nardo Di Maio Raffaele
*/

package Roundabout;

import os.Semaphore;

public class RoundaboutSem extends Roundabout
{
   private Semaphore m = new Semaphore(true);
   private Semaphore[] entrance = new Semaphore[SECTORS];
   private Semaphore[] inside = new Semaphore[SECTORS];

   public RoundaboutSem()
   {
      super();

      for(int i=0; i<SECTORS; i++)
      {
         entrance[i] = new Semaphore(false);
         inside[i] = new Semaphore(false);
      }
   }

   public void enter(int sector)
   {
      m.p();

      enqueued_cars[sector]++;
      printState();

      if(occupied[sector] || occupied[prev(sector)])
      {
         m.v();
         entrance[sector].p();
      }

      occupied[sector]=true;
      enqueued_cars[sector]--;
      printState();

      m.v();
   }

   public int next(int sector)
   {
      int new_sector = succ(sector);

      m.p();
      printState();

      if(occupied[new_sector])
      {
         m.v();
         inside[new_sector].p();
      }

      occupied[sector] = false;
      occupied[new_sector] = true;

      printState();

      if(inside[sector].queue()>0)
         inside[sector].v();
      else if(!occupied[sector] && !occupied[prev(sector)] && entrance[sector].queue()>0)
         entrance[sector].v();

      m.v();

      return new_sector;
   }

   public void exit(int sector)
   {
      m.p();

      occupied[sector] = false;
      served_cars++;
      printState();

      if(inside[sector].queue()>0)
         inside[sector].v();
      else if(!occupied[sector] && !occupied[prev(sector)] && entrance[sector].queue()>0)
         entrance[sector].v();

      m.v();
   }
}
