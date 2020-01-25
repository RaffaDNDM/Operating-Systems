/**
   @author Di Nardo Di Maio Raffaele

   Producer thread
*/

package MultiplePC;

import os.Util;

public class Producer extends Thread
{
   private int id;
   private MultiplePC mpc;

   public Producer(int id, MultiplePC mpc)
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
               System.out.printf("The Producer %2d has already written %2d requests\n", id, satisfied_num);

            satisfied_num += (mpc.write(num, partial));

            //Simulation of process of writing
            Util.rsleep(2000, 4000);

         }

         System.out.printf("The Producer %2d completed writing phase\n", id);
      }
   }
}
