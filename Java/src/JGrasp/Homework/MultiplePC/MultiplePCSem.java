/**
   @author Di Nardo Di Maio Raffaele 1204879
   
   -------------------------------------------------------------
                           Traccia 10
   -------------------------------------------------------------
      Implementazione di Produttori/Consumatori con Semafori
   -------------------------------------------------------------
*/

package MultiplePC;
import os.Semaphore;

public class MultiplePCSem extends MultiplePC
{
   //mutex
   private Semaphore m = new Semaphore(true);
   //array of semaphores (one semaphore for each possible waiting reading condition) 
   private Semaphore[] c_read; 
   //array of semaphores (one semaphore for each possible waiting writing condition) 
   private Semaphore[] c_write; 

   /**
      Buffer for Producer/Consumer logic
      @param size number of marks = number of slots in the buffer
   */
   public MultiplePCSem(int size)
   {
      this.size = size;
      this.free = this.size;
      
      c_read = new Semaphore[size];
      c_write = new Semaphore[size];
      
      System.out.println("----------------------------------------");
      System.out.println("free = "+free +"     size = "+size);
      System.out.println("----------------------------------------");

      //initialization of private semaphores
      for(int i=0; i<size; i++)
      {
         c_write[i] = new Semaphore(false);
         c_read[i] = new Semaphore(false);
      }
   }
   
   public int read(int num, boolean partial)
   {
      int amount;
      
      m.p();
   
      //no possibility of partial consumations
      if(!partial)
      {   
         /**
            Waiting condition
            -> insufficient number of full slots to be read
         */
         if((size-free)<num)
         {
            m.v();
            c_read[num-1].p();
         }
   
         //complete the request
         amount = num;
         //increase the number of completed requests with no parial reading
         served_read++;
      }
      else //possibility of partial consumations
      {
         /**
            Insufficient number of full slots to be read
            read the number of available full slots in this moment
         */
         if((size-free)<num)
            amount = size-free;
         else
         {
            //complete the request
            amount = num;
            //increase the number of completed requests with possible partial reading
            served_p_read++;
         }
      }
      
      //update the available number of free slots
      free+=amount;
      
      //wake up only if there was some readings
      if(amount>0)
      {
         int i;
         
         if((i=max_write())>=0)
            c_write[i].v();
      }
   
      m.v();
   
      return amount;
   }

   public int write(int num, boolean partial)
   {
      int amount;
      
      m.p();
   
      //no possibility of partial allocations
      if(!partial)
      {   
         /**
            Waiting condition
            -> insufficient number of free slots to be written
         */
         if(free < num)
         {
            m.v();
            c_write[num-1].p();
         }

         //complete the request         
         amount = num;
         //increase the number of completed requests with no parial writing
         served_write++;
      }
      else  //possibility of partial allocations
      {
         /**
            Insufficient number of empty slots to be written
            write the number of available empty slots in this moment
         */
         if(free < num)
            amount = free;
         else
         {
            //complete the request
            amount = num;
            //increase the number of completed requests with possible partial writing
            served_p_write++;
         }
      }

      //update the available number of free slots      
      free-=amount;
      
      //wake up only if there was some writings
      if(amount>0)
      {
         int i;
               
         if((i=max_read())>=0)
            c_read[i].v();
      }
      
      m.v();
   
      return amount;
   }
   
   public int max_write()
   {  
      for(int i=free-1; i>=0; i--)
      {
         if(c_write[i].queue()>0)
            return i;
      }  
    
      return -1;
   }

   public int max_read()
   {  
      for(int i=(size-free-1); i>=0; i--)
      {                    
         if(c_read[i].queue()>0)
            return i;
      }  
    
      return -1;
   }
}