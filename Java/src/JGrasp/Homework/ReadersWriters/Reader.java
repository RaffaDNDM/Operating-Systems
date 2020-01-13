/**
   @author Di Nardo Di Maio Raffaele 1204879
*/

package ReadersWriters;

import os.Util;

public class Reader extends Thread
{
   //identifier of the reader
   private int id;
   //instance of ReadersWriters
   private ReadersWriters rw;

   /**
      Reader
      @param id identifier of the reader
      @param rw instance of ReadersWriters
   */
   public Reader(int id, ReadersWriters rw)
   {
      this.id = id;
      this.rw = rw;
   }

   public void run()
   {
      //Request of access to DB
      rw.shared_lock();
      
      //Simulation of Reading phase
      Util.rsleep(3000, 5000);
      
      //Exit from DB
      rw.shared_unlock();
   }
}