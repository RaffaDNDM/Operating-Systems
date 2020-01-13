package ProducerConsumer;

import os.Semaphore;

public class PC_Semaphore extends ProducerConsumer
{
   Semaphore mutex = new Semaphore(true);
   Semaphore occupied = new Semaphore(false);
   Semaphore available = new Semaphore(false);

   public PC_Semaphore()
   {
      initState();
   }
   
   public Object read()
   {
      mutex.p();
      
      if(buff.num_Full()==0)
      {
         mutex.v();
         occupied.p();
      }
      
      Object o = buff.read();
      printState();
      
      if(available.queue()>0)
         available.v();
      
      mutex.v();
      
      return o;
   }
   
   public void write(Object o)
   {
      mutex.p();
      
      if(buff.num_Empty()==0)
      {
         mutex.v();
         available.p();
      }
      
      buff.write(o);
      printState();
      
      if(occupied.queue()>0)
         occupied.v();
      
      mutex.v();
   }
}