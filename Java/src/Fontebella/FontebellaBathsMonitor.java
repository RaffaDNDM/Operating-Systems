/**
@author Di Nardo Di Maio Raffaele
*/

package Fontebella;

import os.Monitor;

public class FontebellaBathsMonitor extends FontebellaBaths
{
	Monitor m = new Monitor();
	Monitor.Condition queueA = m.new Condition();
	Monitor.Condition queueB = m.new Condition();

	public FontebellaBathsMonitor()
	{
		type_app="                        MONITOR DI HOARE";
	}

	public int enterA()
	{
		m.mEnter();

		waitA++;

		if(freeSpouts==0 || (waitB>0 && priority!=0))
			queueA.cWait();

		freeSpouts--;
		waitA--;

		priority=2;

		int actual=spout;
		spout = (spout+1) % NUM_SPOUTS;

		printFontebellaState();

		m.mExit();

		return actual;
	}

	public int enterB()
	{
		m.mEnter();

		waitB++;

		if(freeSpouts==0 || (priority==0 && waitA>0))
			queueB.cWait();


		freeSpouts--;
		waitB--;

		if(waitA>0)
			priority--;

		int actual=spout;
		spout = (spout+1) % NUM_SPOUTS;

		printFontebellaState();

		m.mExit();

		return actual;
	}

	public void endFill()
	{
		m.mEnter();

		freeSpouts++;
		done_clients++;

		if(waitB>0 && (waitA==0 || priority>0))
			queueB.cSignal();
		else if(waitA>0 && (waitB==0 || priority==0))
			queueA.cSignal();

		printFontebellaState();

		m.mExit();
	}
}
