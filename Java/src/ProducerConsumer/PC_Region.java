/**
@author Di Nardo Di Maio Raffaele
*/

package ProducerConsumer;

import os.Region;
import os.RegionCondition;

public class PC_Region extends ProducerConsumer
{
   private Region r = new Region(0);

   public PC_Region()
   {
      initState();
   }

   public Object read()
   {
      Object o;

      r.enterWhen( new RegionCondition()
      {
         public boolean evaluate()
         {
            return buff.num_Full()>0;
         }
      });

      o = buff.read();
      printState();

      r.leave();

      return o;
   }

   public void write(Object o)
   {
      r.enterWhen( new RegionCondition()
      {
         public boolean evaluate()
         {
            return buff.num_Empty()>0;
         }
      });

      buff.write(o);
      printState();

      r.leave();
   }
}
