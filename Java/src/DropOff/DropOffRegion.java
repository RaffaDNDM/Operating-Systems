package DropOff;

import os.Region;
import os.RegionCondition;

public class DropOffRegion extends DropOff
{
	private Region r = new Region(0);
	
	public int inCoda(boolean prio)
	{
		if(prio)
		{
			r.enterWhen();
			wprio++;
			r.leave();
			
			r.enterWhen(new RegionCondition()
			{
				public boolean evaluate()
				{
					return punLib!=0;
				}
			});
			
			wprio--;
			punLib--;
		
			r.leave();
		}
		else
		{
			r.enterWhen();
			wnorm++;
			r.leave();
				
			r.enterWhen(new RegionCondition()
			{
				public boolean evaluate()
				{
					return punLib!=0 && wprio==0;
				}
			});
				
			wnorm--;
			punLib--;
			
			r.leave();
		}
		
		r.enterWhen();
		
		int pos=0;
		
		if(pLib[1])
		{
			pos=1;
		}
		
		pLib[pos]=false;
		r.leave();
		return pos;
	}

	public void regok(int pos)
	{
		if(pos==1)
		{
			r.enterWhen(new RegionCondition()
			{
				public boolean evaluate()
				{
					return libCD;
				}
			});
		
			r.leave();
		}
		
		r.enterWhen();
		if(pos==0)
		{
			libCD=false;
		}
		r.leave();
	}

	public void term(int pos)
	{
		r.enterWhen();
		
		punLib++;
		
		pLib[pos]=true;
		
		if(pos==0)
			libCD=true;
		
		r.leave();
	}

}
