package Fontebella;

import os.Util;
import os.Monitor;
import os.Monitor.Condition;

public class FontebellaMonitor extends Fontebella
{  
   private Monitor m = new Monitor();
   private Condition codaA = m.new Condition();
   private Condition codaB = m.new Condition();
 
   public int entraCodaA()
   {
      int pos;
      
      m.mEnter();
      
      clientiA++;
      
      if(zampilliLiberi==0 || (clientiB>0 && stat!=0))
         codaA.cWait();
      
      stat=2;
      zampilliLiberi--;
      ultimoZampillo = (ultimoZampillo+1)%8;
      pos = ultimoZampillo;
      
      clientiA--;
      stampaSituazione();
      
      m.mExit();
      
      return pos;      
   }
   
   public int entraCodaB()
   {
      int pos;
   
      m.mEnter();
      
      clientiB++;
      
      if(zampilliLiberi==0 || (clientiA>0 && stat==0))
         codaB.cWait();
      
      if(clientiA>0)
         stat--;

      zampilliLiberi--;
      ultimoZampillo = (ultimoZampillo+1)%8;
      clientiB--;
      stampaSituazione();
      pos = ultimoZampillo;
      
      m.mExit();
      
      return pos;      
   }
   
   public void fineRiempimento()
   {
      m.mEnter();
      
      zampilliLiberi++;
      serviti++;
      stampaSituazione();
      
      if(clientiB>0 && stat!=0)
         codaB.cSignal();
      else if(clientiA>0 && stat==0)
         codaA.cSignal();
      else if(clientiB>0)   
         codaB.cSignal();
      else if(clientiA>0)
         codaA.cSignal();
      
      m.mExit();         
   }
   
   public static void main(String[] args)
   {
      Fontebella f = new FontebellaMonitor();
      
      for(int i=0; i<CLIENTI; i++)
      {
         int choice = Util.randVal(1, 10);
         
         if(choice<4)
            new ClienteB(i, f).start();               
         else
            new ClienteA(i, f).start();            
      
         Util.rsleep(Long.parseLong(args[0]), Long.parseLong(args[1]));
      }
   }
}