package GestioneCarrelli;

import os.Monitor;
import os.Monitor.Condition;
import os.Util;

public  class SmistaMon extends Smista
{  
   private Monitor m = new Monitor();
   private Condition c0 = m.new Condition();
   private Condition c1 = m.new Condition();
   private Condition c2 = m.new Condition();
   private Condition b = m.new Condition();
   
   private boolean[] stat = new boolean[3];
   
   public SmistaMon()
   {
      for(int j=0; j<3; stat[j++]=true);
   }
   
   public int instrada(final int i)
   { 
      int elevatore;
   
      m.mEnter();
      
         enqueued[i]++;
         int ticket_in = ticket[i]++;
      
         stampaSituazione();
         
         if(!I1 || (!B[0] && !B[1]) || !C[i])
         {
            switch(i)
            {
               case 0:
                  c0.cWait();
                  break;
               
               case 1:
                  c1.cWait();               
                  break;
               
               case 2:
                  c2.cWait();               
                  break;
            }
         }
         
         serv[i]++;
         elevatore = B[0]?0:1;
         I1 = false;
         freeB--;
         enqueued[i]--;
         stat[i]=false;
         stampaSituazione();
      
      m.mExit();
      
      return elevatore;
   }
   
   public void outI1(int h)
   {  
      m.mEnter();
      
         I1 = true;
         B[h]=false;
         stampaSituazione();
         
      m.mExit();      
   }
   
   public int outB(int h)
   { 
      int exit;
   
      m.mEnter();
      
         if(!I2)
            b.cWait();
            
         I2=false;
         freeB++;
         B[h]=true;
         exit = ((Carrello) Thread.currentThread()).i;
         stampaSituazione();
      
      m.mExit();
      
      return exit;      
   }
   
   public void inC(int i)
   { 
      m.mEnter();
      
         I2=true;
         freeC--;
         C[i] = false;
         stampaSituazione();
      
      m.mExit();      
   }
   
   public void outC(int i)
   { 
      m.mEnter();
      
         C[i]=true;
         served[i]++;
         freeC++;
         stat[i]=true;
         stampaSituazione();
         
         if(freeB==1 || freeB==0)
            b.cSignal();
         
         else if(freeB>0 && I1 && enqueued[i]>0)
         {
            switch(i)
            {
               case 0:
                  c0.cSignal();
               
               case 1:
                  c1.cSignal();
                  
               case 2:
                  c2.cSignal();
            }
         }
         else if(freeB>0 && I1)
         {
            int index = (i+1)%3;
            
            if(stat[index] && enqueued[index]>0 && C[index])
            {
               switch(index)
               {
                  case 0:
                     c0.cSignal();
                  
                  case 1:
                     c1.cSignal();
                     
                  case 2:
                     c2.cSignal();
               }            
            }
            else if(stat[(index+1)%3] && enqueued[(index+1)%3]>0 && C[(index+1)%3])
            {
               switch((index+1)%3)
               {
                  case 0:
                     c0.cSignal();
                  
                  case 1:
                     c1.cSignal();
                     
                  case 2:
                     c2.cSignal();
               }            
            }
         }
      
      m.mExit();      
   }
   
   public static void main(String[] args)
   {
      Smista s = new SmistaMon();
   
      for(int i=0; i<CARRELLI; i++)
      {
         new Carrello(i, s).start();
         
         long min=Long.parseLong(args[0]);
         long max=Long.parseLong(args[1]);
         
         Util.rsleep(min,max);
      }
   }
}