package Spinlock;

import os.Semaphore;
import os.Util;

public class Spinlock extends NumberSemaphore
{
	private final static long TIMEOUT = 2000L;
	private int max_marks;
	private int available_marks;
	
	private Semaphore m = new Semaphore(true);
	private Semaphore queue = new Semaphore(false);
	
	public Spinlock(int marks)
	{
		this.available_marks=marks;
		this.max_marks=marks;
	}
	
	public Spinlock(int initial, int marks)
	{
		this.available_marks=initial;
		this.max_marks=marks;
	}
	
	public synchronized void p()
	{
		int array[] = {8, 4, 2, 1, 2, 4};
		
		int i=-1;
		while(available_marks==0)
		{
			i=(i+1)%6;
			
			try
			{
				Util.sleep(array[i]*TIMEOUT);
				wait();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}
	}

	public synchronized void p(long timeout)
	{
	
	}

	public synchronized void v()
	{
	
	}

}
