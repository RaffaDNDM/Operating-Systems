/**
@author Di Nardo Di Maio Raffaele
*/

package Roundabout;

import os.Util;

public class Vehicle extends Thread
{
   private Roundabout r;
   int id, sector;

   public Vehicle(Roundabout r, int id, int sector)
   {
      this.r = r;
      this.id = id;
      this.sector = sector;
   }

   public void run()
   {
      r.enter(sector);

      int num_sectors = Util.randVal(1, r.SECTORS);

      for(int i=1; i<=num_sectors; i++)
      {
         Util.rsleep(500, 4000);
         sector = r.next(sector);
      }

      r.exit(sector);
   }
}
