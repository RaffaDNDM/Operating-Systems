/**
@author Di Nardo Di Maio Raffaele
*/

package ProducerConsumer;

public class Buffer
{
   private Object[] buff;
   private int num;
   private int empty;
   private int full = 0;
   private int read_pointer=0;
   private int write_pointer=0;

   public Buffer(int num)
   {
      this.num = num;
      empty = num;
      buff = new Object[num];
   }

   public Object read()
   {
      Object now = buff[read_pointer];
      System.out.println("Read slot: "+now);
      read_pointer = (read_pointer+1)%num;
      empty++;
      full--;
      return now;
   }

   public void write(Object o)
   {
      buff[write_pointer]=o;
      System.out.println("Written slot: "+o);
      write_pointer = (write_pointer+1)%num;
      empty--;
      full++;
   }

   public int num_Empty()
   {
      return empty;
   }

   public int num_Full()
   {
      return full;
   }
}
