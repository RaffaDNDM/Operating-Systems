package Fontebella;

import os.Semaphore;

public class FontebellaBathsSemaphore extends FontebellaBaths
{
	Semaphore m = new Semaphore(1,1);
	Semaphore privA = new Semaphore(0, 1);
	Semaphore privB = new Semaphore(0, 1);
	
	public FontebellaBathsSemaphore()
	{
		type_app="                         SEMAFORO PRIVATO";
	}

	public int enterA()
	{
		m.p();
		
		waitA++;
		
		if(freeSpouts==0 || (waitB>0 && priority>0))
		{
			m.v();
			privA.p();
		}
		
		waitA--;
		freeSpouts--;
		
		priority=2;
		
		int actual = spout;
		spout = (spout+1) % NUM_SPOUTS;
		
		printFontebellaState();
		
		m.v();
		
		return actual;
	}

	public int enterB()
	{
		m.p();
		
		waitB++;
		
		if(freeSpouts==0 || (waitA>0 && priority==0))
		{
			m.v();
			privB.p();
		}
		
		waitB--;
		freeSpouts--;
		
		if(waitA>0)
			priority--;
		
		int actual = spout;
		spout = (spout+1) % NUM_SPOUTS;
		
		printFontebellaState();
		
		m.v();
		
		return actual;
	}

	public void endFill()
	{
		m.p();
		
		freeSpouts++;
		done_clients++;
		
		printFontebellaState();
		
		if(waitB>0 && (waitA==0 || priority>0))
			privB.v();
		else if(waitA>0 && (waitB==0 || priority==0))
			privA.v();
		else
			m.v();
	}
}
