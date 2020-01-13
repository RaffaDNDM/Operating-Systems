package Allocator12;

import os.Region;
import os.RegionCondition;

public class All12_Region extends Allocator12
{
   private Region r = new Region(0);

   public All12_Region(int num, long min, long max)
   {
      init_state(num, min, max);   
   }
   
   public void req2_1()
   {
      r.enterWhen(new RegionCondition()
      {
         public boolean evaluate()
         {
            return num>=2;
         }
      });
      
      num--;
      printState();
      
      r.leave();
   }
   
   public void req2_all()
   {
      r.enterWhen(new RegionCondition()
      {
         public boolean evaluate()
         {
            return num>=2;
         }
      });
      
      num-=2;
      printState();
      
      r.leave();   
   }
   
   public void req1()
   {
      r.enterWhen(new RegionCondition()
      {
         public boolean evaluate()
         {
            return num>=1;
         }
      });
      
      num--;
      printState();      
      
      r.leave();   
   }
   
   public void end(int num_res)
   { 
      r.enterWhen();
      
      num+=num_res;
      count++;
      
      printState();
      printCount();
      
      r.leave();
   }
}