package ProducerConsumer;

import os.Monitor;
import os.Monitor.Condition;

public class PC_Monitor_Parallel extends ProducerConsumer
{
   private Monitor mProd = new Monitor();
   private Monitor mCons = new Monitor();
   private Condition available = mProd.new Condition();
   private Condition occupied = mCons.new Condition();   
   
   public PC_Monitor_Parallel()
   {
      initState();
   }
   
   public Object read()
   {
      mCons.mEnter();

      if(buff.num_Full()==0)
         occupied.cWait();
      
      Object out = buff.read();
      printState();
      
      if(available.queue()>0)
         available.cSignal();

      mCons.mExit();
      return out;
   }
   
   public void write(Object o)
   {
      mProd.mEnter();

      if(buff.num_Empty()==0)
         available.cWait();
      
      buff.write(o);
      printState();
      
      if(occupied.queue()>0)
         occupied.cSignal();

      mProd.mExit();
   }
}