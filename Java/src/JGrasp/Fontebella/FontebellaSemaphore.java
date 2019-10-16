package Fontebella;

import os.Semaphore;
import os.Util;

public class FontebellaSemaphore extends Fontebella
{   
   private Semaphore m = new Semaphore(true);
   private Semaphore codaA = new Semaphore(false);
   private Semaphore codaB = new Semaphore(false);

   public int entraCodaA()
   {
      int pos;
   
      m.p();
      
      clientiA++;
      
      if(zampilliLiberi==0 || (clientiB>0 && stat!=0))
      {
         m.v();
         codaA.p();
      }
         
      clientiA--;
      zampilliLiberi--;
      stat=2;
      ultimoZampillo = (ultimoZampillo + 1)%8;
      pos = ultimoZampillo;
         
      m.v();
   
      return ultimoZampillo;
   }
   
   public int entraCodaB()
   {
      int pos;
   
      m.p();
      
      clientiB++;
      
      if(zampilliLiberi==0 || (clientiA>0 && stat==0))
      {
         m.v();
         codaB.p();
      }
         
      clientiB--;
      zampilliLiberi--;
      
      if(clientiA>0)
         stat--;
      
      ultimoZampillo = (ultimoZampillo + 1)%8;
      pos = ultimoZampillo;
         
      m.v();
   
      return pos;   
   }
   
   public void fineRiempimento()
   {
      m.p();
      
      zampilliLiberi++;
      serviti++;
      stampaSituazione();
      
      if(clientiB>0 && stat!=0)
         codaB.v();
      else if(clientiA>0 && stat==0)
         codaA.v();
      else if(clientiB>0)
         codaB.v();
      else if(clientiA>0)
         codaA.v();
      
      m.v();
   }

   public static void main(String[] args)
   {
      Fontebella f = new FontebellaSemaphore();
      
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