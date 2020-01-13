package Allocator12;

import os.Monitor;
import os.Monitor.Condition;

public class All12_Monitor extends Allocator12
{
   Monitor m = new Monitor();
   Condition requested1 =m.new Condition();
   Condition requested2 =m.new Condition();

   public All12_Monitor(int num, long min, long max)
   {
      init_state(num, min, max);
   }
   
   public void req2_1()
   {
      m.mEnter();
      
      if(num<2)
         requested2.cWait();
         
      num--;
      printState();
      
      m.mExit();
   }
   
   public void req2_all()
   {
      m.mEnter();
      
      if(num<2)
         requested2.cWait();
         
      num-=2;
      printState();
      
      m.mExit();
   }
   
   public void req1()
   {
      m.mEnter();
      
      if(num<1)
         requested1.cWait();
         
      num--;
      printState();      
      
      m.mExit();
   }
   
   public void end(int num_res)
   { 
      m.mEnter();
      
      num+=num_res;
      count++;
      printCount();
      printState();
      
      if(num_res==2)
      {
         if(requested2.queue()>0)
            requested2.cSignal();
         else if(requested1.queue()>0)
            requested1.cSignal();  
      }
      else if(num_res==1 && requested1.queue()>0)
         requested1.cSignal();  
      
      m.mExit();
   }
}