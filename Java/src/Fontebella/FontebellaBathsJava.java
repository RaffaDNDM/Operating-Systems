/**
@author Di Nardo Di Maio Raffaele
*/

package Fontebella;

public class FontebellaBathsJava extends FontebellaBaths
{
	private int ticketA=0;
	private int ticketB=0;

	private int serviceA=0;
	private int serviceB=0;

	public FontebellaBathsJava()
	{
		type_app="                        MONITOR DI JAVA";
	}

	public synchronized int enterA()
	{
		int ticket = ticketA++;
		waitA++;

		while(ticket!=serviceA || freeSpouts==0 || (priority>0 && waitB>0))
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

		serviceA++;
		waitA--;
		freeSpouts--;

		printFontebellaState();

		priority=2;

		int actual= spout;
		spout = (spout +1) % NUM_SPOUTS;

		return actual;
	}

	public synchronized int enterB()
	{
		int ticket = ticketB++;
		waitB++;

		while(ticket!=serviceB || freeSpouts==0 || (priority==0 && waitA>0))
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

		waitB--;
		serviceB++;
		freeSpouts--;

		printFontebellaState();

		if(waitA>0)
			priority--;

		int actual = spout;
		spout=(spout+1)%NUM_SPOUTS;

		return actual;
	}

	public synchronized void endFill()
	{
		freeSpouts++;
		done_clients++;
		printFontebellaState();

		notifyAll();
	}
}
