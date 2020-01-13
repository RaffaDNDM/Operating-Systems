package Roundabout;

import os.Monitor;
import os.Monitor.Condition;

public class RoundaboutMon extends Roundabout
{  
   private Monitor m = new Monitor();
   private Condition[] entrance = new Condition[SECTORS];
   private Condition[] inside = new Condition[SECTORS];

   public RoundaboutMon()
   {
      super();
      
      for(int i=0; i<SECTORS; i++)
      {
         entrance[i] = m.new Condition();
         inside[i] = m.new Condition();
      }
   }

   public void enter(int sector)y
   {
      m.mEnter();
   
      enqueued_cars[sector]++;
      printState();
      
      if(occupied[sector] || occupied[prev(sector)])
         entrance[sector].cWait();
      
      occupied[sector]=true;
      enqueued_cars[sector]--;
      printState();
         
      m.mExit();
   }
   
   public int next(int sector)
   {
      int new_sector = succ(sector);
      
      m.mEnter();
      
      printState();
      
      if(occupied[new_sector])
         inside[new_sector].cWait();
      
      occupied[sector] = false;
      occupied[new_sector] = true;
      printState();
      
      if(inside[sector].queue()>0)
         inside[sector].cSignal();
      else if(!occupied[sector] && !occupied[prev(sector)] && entrance[sector].queue()>0)
         entrance[sector].cSignal();
      
      m.mExit();
      
      return new_sector;
   }
   
   public void exit(int sector)
   {
      m.mEnter();
      
      occupied[sector] = false;
      served_cars++;
      printState();      

      if(inside[sector].queue()>0)
         inside[sector].cSignal();
      else if(!occupied[sector] && !occupied[prev(sector)] && entrance[sector].queue()>0)
         entrance[sector].cSignal();
      
      m.mExit();
   }
}