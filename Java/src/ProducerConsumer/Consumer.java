package ProducerConsumer;

import java.util.Random;

import os.Util;

public class Consumer extends Thread
{
	private ProducerConsumer pc;
	private int ticket;
	
	public Consumer(ProducerConsumer pc)
	{
		this.pc=pc;
	}
	
	public void run()
	{
		for(int i=1;;i++)
		{
			Util.rsleep(300, 1000);
			Random random = new Random();
			int r = random.nextInt(10);
			System.out.println("Richiesta di scrittura "+i+" di "+r+" celle");
			
			while(r!=0)
			{
				Util.rsleep(100, 400);
				r = pc.read(r);
			}
			
			System.out.println("Richiesta di scrittura "+i+" completata");
		}
	}
	
	public int getTicket()
	{
		return ticket;
	}
	
	public void setTicket(int num)
	{
		ticket=num;
	}
	
}