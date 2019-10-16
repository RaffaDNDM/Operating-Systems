package ProducerConsumer;

import java.util.Random;

import os.Util;

public class Producer extends Thread
{
	private ProducerConsumer pc;
	private int ticket;
	
	public Producer (ProducerConsumer pc)
	{
		this.pc=pc;
		ticket=-1;
	}
	
	public void run()
	{
		for(int i=1;;i++)
		{
			Util.rsleep(300, 1000);
			Random random = new Random();
			int r = random.nextInt(15);
			System.out.println("Richiesta di lettura "+i+" di "+r+" celle");
			
			while(r!=0)
			{
				Util.rsleep(100, 400);
				r = pc.write(r);
			}
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
