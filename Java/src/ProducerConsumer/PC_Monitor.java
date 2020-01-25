/**
@author Di Nardo Di Maio Raffaele
*/

package ProducerConsumer;

import os.Monitor;
import os.Monitor.Condition;

public class PC_Monitor extends ProducerConsumer
{
   private Monitor m = new Monitor();
   private Condition available = m.new Condition();
   private Condition occupied = m.new Condition();

   public PC_Monitor()
   {
      initState();
   }

   public Object read()
   {
      m.mEnter();

      if(buff.num_Full()==0)
         occupied.cWait();

      Object out = buff.read();
      printState();

      if(available.queue()>0)
         available.cSignal();

      m.mExit();
      return out;
   }

   public void write(Object o)
   {
      m.mEnter();

      if(buff.num_Empty()==0)
         available.cWait();

      buff.write(o);
      printState();

      if(occupied.queue()>0)
         occupied.cSignal();

      m.mExit();
   }
}
