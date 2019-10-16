package ProducerConsumer;

import java.util.Random;

import os.Util;

public class ProducerJ extends Thread
{
	private ProducerConsumerJ pc;
	private int ticket;
	
	public ProducerJ (ProducerConsumerJ pc)
	{
		this.pc=pc;
		ticket=-1;
	}
	
	public void run()
	{
		for(int i=1;;i++)
		{
			setTicket(-1);
			Util.rsleep(300, 1000);
			Random random = new Random();
			int r = random.nextInt(14);
			System.out.println("Richiesta di scrittura "+i+" di "+r+" celle");
			
			while(r!=0)
			{
				Util.rsleep(100, 400);
				r = pc.write(r, this);
			}
			
			pc.completedWrite();
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
