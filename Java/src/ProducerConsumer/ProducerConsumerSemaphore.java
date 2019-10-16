package ProducerConsumer;

import os.MutexSem;
import os.Semaphore;

public class ProducerConsumerSemaphore extends ProducerConsumer 
{
	private MutexSem mutex;
	private Semaphore occupied;
	private Semaphore free;
	
	public ProducerConsumerSemaphore()
	{
		mutex = new MutexSem();
		occupied = new Semaphore(0, DIM_BUFFER);
		free= new Semaphore(DIM_BUFFER, DIM_BUFFER);
	}

	public int read(int num_read)
	{
		mutex.p();
		
		int i=0;
		boolean flag=true;
		
		while (i<num_read && flag)
		{
			if(occupied.value()>0)
			{
				occupied.p();
				free.v();
				i++;
				num_free++;
				num_occupied--;
			}
			else
				flag=false;
		}

		if(i==num_read)
		{
			num_read_done++;
		}

		state();
		mutex.v();
		
		return i;
	}
	
	
	public int write(int num_write)
	{
		mutex.p();
		
		int i=0;
		boolean flag=true;
		
		while (i<num_write && flag)
		{
			if(free.value()>0)
			{
				free.p();
				occupied.v();
				i++;
				num_free--;
				num_occupied++;
			}
			else
				flag=false;
		}
		
		if(i==num_write)
		{
			num_write_done++;
		}
		
		state();
		mutex.v();
	
		return i;
	}
	
	public static void main(String[] args)
	{
		ProducerConsumerSemaphore pc = new ProducerConsumerSemaphore();
		Producer producer = new Producer(pc);
		Consumer consumer = new Consumer(pc);
		producer.start();
		consumer.start();
	}
}
