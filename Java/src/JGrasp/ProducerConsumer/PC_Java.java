package ProducerConsumer;

public class PC_Java extends ProducerConsumer
{
   public PC_Java()
   {
      initState();
   }
   
   public synchronized Object read()
   {
      while(buff.num_Full()==0)
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
      
      Object o = buff.read();
      printState();
      
      notifyAll();
   
      return o;
   }
   
   public synchronized void write(Object o)
   {
      while(buff.num_Empty()==0)
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

      buff.write(o);
      printState();
      
      notifyAll();
   }
}