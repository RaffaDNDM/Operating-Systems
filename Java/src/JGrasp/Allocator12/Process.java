package Allocator12;

import os.Util;

public class Process extends Thread
{
   /**
      Types of requests
      0 = request of a single resource
      1 = request of two resources (all in one step)
      2 = request of two resources (in two steps)
   */
   private int[] cases = {1, 2, 2}; 
   private int id;
   private int type;
   private Allocator12 all;

   public Process(int id, int type, Allocator12 all)
   {
      this.type = type;
      this.id = id;
      this.all = all;
   }
   
   public void run()
   {
      switch(type)
      {
         case(0):
         {
            all.req1();
            break;
         }

         case(1):
         {   
            all.req2_all();
            break;
         }

         case(2):
         {
            all.req2_1();
            Util.rsleep(all.min(), all.max());
            all.req1();
            break;
         }
      }
      
      Util.rsleep(all.min(), all.max());
      all.end(cases[type]);
   }
}