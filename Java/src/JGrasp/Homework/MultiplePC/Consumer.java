/**
   @author Di Nardo Di Maio Raffaele 1204879

   Consumer thread
*/

package MultiplePC;

import os.Util;

public class Consumer extends Thread
{
   private int id;
   private MultiplePC mpc;

   public Consumer(int id, MultiplePC mpc)
   {
      this.id = id;
      this.mpc = mpc;
   }
   
   public void run()
   {
      while(true)
      {
         int size = mpc.getSize();
         boolean partial = (Util.randVal(1,10)<4)? true : false;      
         
         int num = Util.randVal(1, size);
         int satisfied_num = 0;
      
         while(satisfied_num<num)
         {
            if(satisfied_num>0)
               System.out.printf("The Consumer %2d has already read %2d requests\n", id, satisfied_num);  
         
            satisfied_num += (mpc.read(num, partial));
            
            //Simulation of process of reading
            Util.rsleep(2000, 4000);
         }
                
         System.out.printf("The Consumer %2d completed reading phase\n", id);
      }
   }
}