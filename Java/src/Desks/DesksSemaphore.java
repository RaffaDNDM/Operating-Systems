package Desks;

import os.Semaphore;

public class DesksSemaphore extends Desks
{
	private int num_desks;
	private Semaphore mutex = new Semaphore(true);
	private Semaphore queue;
	private boolean free[];
	
	public DesksSemaphore(int num_desks)
	{
		this.num_desks=num_desks;
		free = new boolean [num_desks];
		for(int i=0;i<num_desks; free[i++]=true);
		queue = new Semaphore(num_desks);
	}
	
	public int enterQueue()
	{
		mutex.p();
		queue.p();
	
		int i=0;
		for(i=0; i<num_desks; i++) 
		{
			if(free[i]==true)
				break;
		}
		
		free[i]=false;
		mutex.v();
	
		return i;
	}
	
	public void exitQueue(int desk)
	{
		mutex.p();
		free[desk]=true;
		queue.v();
		mutex.v();
	}
}
