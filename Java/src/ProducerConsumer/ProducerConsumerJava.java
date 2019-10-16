package ProducerConsumer;

//import java.util.Random;

public class ProducerConsumerJava extends ProducerConsumerJ
{
	private static int ticket_producer=0;
	private static int ticket_consumer=0;
	private int ticket_now_p=1;
	private int ticket_now_c=1;
	
	public synchronized int read(int num_read, ConsumerJ c)
	{	
		if(c.getTicket()==-1)
		{	
			ticket_consumer++;
			int ticket=ticket_consumer;
			c.setTicket(ticket);
		}
		
		System.out.println("ticket_now_c: "+ticket_now_c+"   ticket_c: "+c.getTicket());
		while(num_occupied==0 || ticket_now_c!=c.getTicket())
		{
			try
			{
				wait();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		while(num_occupied>0 && num_read>0)
		{
			num_read--;
			num_occupied--;
			num_free++;
		}
		
		state();
		return num_read;
	}

	public synchronized int write(int num_write, ProducerJ p)
	{
		if(p.getTicket()==-1)
		{	
			ticket_producer++;
			int ticket= ticket_producer;
			p.setTicket(ticket);
		}
		
		System.out.println("ticket_now_c: "+ticket_now_p+"    ticket_c: "+p.getTicket());
		while(num_free==0 || ticket_now_p != p.getTicket())
		{
			try
			{
				wait();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		while(num_free>0 && num_write>0)
		{
			num_write--;
			num_free--;
			num_occupied++;
		}
			
		state();
		return num_write;
	}
	
	public synchronized void completedRead()
	{
		num_read_done++;
		ticket_now_c++;
		
		notifyAll();
	}
	
	public synchronized void completedWrite()
	{	
		num_write_done++;
		ticket_now_p++;
	
		notifyAll();
	}
	
	public static void main(String[] args)
	{
		ProducerConsumerJava pc = new ProducerConsumerJava();
		ProducerJ producer = new ProducerJ(pc);
		ConsumerJ consumer = new ConsumerJ(pc);
		producer.start();
		consumer.start();
	}
}
