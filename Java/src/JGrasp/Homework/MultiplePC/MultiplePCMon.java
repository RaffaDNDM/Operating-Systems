/**
   @author Di Nardo Di Maio Raffaele 1204879
   
   ----------------------------------------------------------------
                             Traccia 11
   ----------------------------------------------------------------
    Implementazione di Produttori/Consumatori con Monitor di Hoare
   ----------------------------------------------------------------
*/

package MultiplePC;
import os.Monitor;
import os.Monitor.Condition; 
import java.util.ArrayList;

public class MultiplePCMon extends MultiplePC
{
   //monitor
   private Monitor m = new Monitor();
   //array of condition queues (one condition queue for each possible waiting reading condition)
   private Condition[] c_read;
   //array of condition queues (one condition queue for each possible waiting writing condition)
   private Condition[] c_write;

   /**
      Buffer for Producer/Consumer logic
      @param size number of marks = number of slots in the buffer
   */
   public MultiplePCMon(int size)
   {
      this.size = size;
      this.free = this.size;
      
      c_write = new Condition[size];
      c_read = new Condition[size];

      System.out.println("----------------------------------------");
      System.out.println("free = "+free +"     size = "+size);
      System.out.println("----------------------------------------");

      //initialization of private semaphores
      for(int i=0; i<size; i++)
      {
         c_write[i] = m.new Condition();
         c_read[i] = m.new Condition();
      }
   }
   
   public int read(int num, boolean partial)
   {
      int amount;
      
      m.mEnter();
   
      //no possibility of partial consumations
      if(!partial)
      {   
         /**
            Waiting condition
            -> insufficient number of full slots to be read
         */
         if((size-free)<num)
            c_read[num-1].cWait();
   
         
         //complete the request
         amount = num;
         //increase the number of completed requests with no parial reading
         served_read++;
      }
      else  //possibility of partial consumations
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
            c_write[i].cSignal();
      }
      
      m.mExit();
   
      return amount;
   }

   public int write(int num, boolean partial)
   {
      int amount;
      
      m.mEnter();
   
      //no possibility of partial allocations
      if(!partial)
      {   
         /**
            Waiting condition
            -> insufficient number of free slots to be written
         */
         if(free < num)
            c_write[num-1].cWait();
   
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
            c_read[i].cSignal();
      }
      
      m.mExit();
   
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