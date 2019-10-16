package ProducerConsumer;

import os.Monitor;

public class ProducerConsumerMonitor extends ProducerConsumer
{
	private Monitor m= new Monitor();
	private Monitor.Condition queue = m. new Condition();
	
	public int read(int num_read)
	{
		m.mEnter();
		
		if(num_occupied==0)
			queue.cWait();
		
		while(num_occupied>0 && num_read>0)
		{
			num_occupied--;
			num_free++;
			num_read--;
		}
		
		queue.cSignal();
		
		if(num_read==0)
		{
			num_read_done++;
		}
		
		state();
		m.mExit();
		return num_read;
	}

	public int write(int num_write)
	{
		m.mEnter();
		
		if(num_free==0)
			queue.cWait();
		
		while(num_free>0 && num_write>0)
		{
			num_occupied++;
			num_free--;
			num_write--;
		}
		
		queue.cSignal();
		
		if(num_write==0)
		{
			num_write_done++;
		}
		
		state();
		m.mExit();
		return num_write;
	}

	public static void main(String[] args)
	{
		ProducerConsumerMonitor pc = new ProducerConsumerMonitor();
		Producer producer = new Producer(pc);
		Consumer consumer = new Consumer(pc);
		producer.start();
		consumer.start();
	}
}