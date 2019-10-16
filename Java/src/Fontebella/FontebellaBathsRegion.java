package Fontebella;

import os.Region;
import os.RegionCondition;

public class FontebellaBathsRegion extends FontebellaBaths
{
	private Region r;
	
	public FontebellaBathsRegion()
	{
		type_app = "                         REGIONE CRITICA";
		r = new Region(0);
	}

	public int enterA()
	{
		waitA++;
		
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return freeSpouts>0 && (waitB==0 || priority==0);
			}
		});
		
		waitA--;
		freeSpouts--;
		
		priority=2;
		
		int actual = spout;
		spout = (spout +1) % NUM_SPOUTS;
		
		printFontebellaState();
		
		r.leave();
		
		return actual;
	}

	public int enterB()
	{
		waitB++;
		
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return freeSpouts>0 && (waitA==0 || priority>0);
			}
		});
		
		waitB--;
		freeSpouts--;
		
		if(waitA>0)
			priority--;
		
		int actual = spout;
		spout = (spout +1) % NUM_SPOUTS;
		
		printFontebellaState();
		
		r.leave();
		
		return actual;
	}

	public void endFill()
	{
		r.enterWhen();
		freeSpouts++;
		done_clients++;
		
		printFontebellaState();
		r.leave();
	}
}
