/**
   @author Di Nardo Di Maio Raffaele 1204879
*/

package ReadersWriters;

import os.Util;

public class Writer extends Thread
{
   //identifier of the writer
   private int id;
   //instance of ReadersWriters
   private ReadersWriters rw;

   /**
      Writer
      @param id identifier of the writer
      @param rw instance of ReadersWriters
   */
   public Writer(int id, ReadersWriters rw)
   {
      this.id = id;
      this.rw = rw;
   }

   public void run()
   {
      //Request of access to DB
      rw.exclusive_lock();
      
      //Simulation of Writing phase
      Util.rsleep(3000, 5000);
      
      //Exit from DB
      rw.exclusive_unlock();
   }
}