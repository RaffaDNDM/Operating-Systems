package Allocator12;

public abstract class Allocator12
{
   protected int num; //# resources in the pool
   protected int[] resources;
   protected long min=0;   
   protected long max=0;
   protected int count=0;
      
   protected void init_state(int num_resources, long min_time, long max_time)
   {
      num = num_resources;
      min = min_time;
      max = max_time;
      resources = new int[num_resources];
   }
   
   protected void printState()
   {
      System.out.println("Number of resources in the pool: "+num);
   }

   protected void printCount()
   {
      System.out.println("Number of completed processes: "+count);
   }
      
   public abstract void req2_1();
   public abstract void req1();
   public abstract void req2_all();
   public abstract void end(int num);
  
   public long min()
   {
      return min;
   }

   public long max()
   {
      return max;
   }
}