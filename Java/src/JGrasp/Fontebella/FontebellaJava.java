package Fontebella;

import os.Util;

public class FontebellaJava extends Fontebella
{
   private int ticketA = 0;
   private int serviceA = 0;
   private int ticketB = 0;
   private int serviceB = 0;

   public synchronized int entraCodaA()
   {
      int ticket = ticketA++;
      clientiA++;
      stampaSituazione();
      
      while(ticket!=serviceA || zampilliLiberi==0 || (clientiB>0 && stat!=0))
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interruzione");
         }
      }
      
      serviceA++;
      stat=2;
      clientiA--;
      zampilliLiberi--;
      ultimoZampillo = (ultimoZampillo +1)%8; 
      stampaSituazione();

      return ultimoZampillo;
   }
   
   public synchronized int entraCodaB()
   {
      int ticket = ticketB++;
      clientiB++;
      stampaSituazione();
      
      while(ticket!=serviceB || zampilliLiberi==0 || (clientiA>0 && stat==0))
      {
         try
         {
            wait();
         }
         catch(InterruptedException e)
         {
            System.out.println("Interruzione");
         }
      }
      
      serviceB++;
      
      if(clientiA>0)
         stat--;
         
      clientiB--;
      zampilliLiberi--;
      ultimoZampillo = (ultimoZampillo +1)%8; 

      stampaSituazione();
      return ultimoZampillo;
   }
   
   public synchronized void fineRiempimento()
   {
      zampilliLiberi++;
      serviti++;
      
      stampaSituazione();
      notifyAll();
   }
   
   public static void main(String[] args)
   {
      Fontebella f = new FontebellaJava();
      
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