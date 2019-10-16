package CrossRoad;

import os.Region;
import os.RegionCondition;

public class CrossRoadRegion extends CrossRoad
{
	public static final int CARS=20;
	private Region r = new Region(0);
	
	
	public CrossRoadRegion()
	{
		type_app = "                   REGIONE CRITICA";
	}
	
	public void enterCrossroadNS()
	{
		r.enterWhen();
		waitNS++;
		printCrossroadState();
		r.leave();
		
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return freeCrossR>0 && (waitEW==0 || !isEW) ;
			}
		});
		
		waitNS--;
		
		if(waitEW>0)
			max_NS--;
		
		if(max_NS==0)
		{
			isEW = true;
			max_NS = MAX_CAPACITY;
		}
		
		freeCrossR--;
		
		r.leave();
	}

	public void enterCrossroadEW()
	{
		r.enterWhen();
		waitEW++;
		printCrossroadState();
		r.leave();
		
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return freeCrossR>0 && (waitNS==0 || isEW) ;
			}
		});
		
		waitEW--;
		
		if(waitNS>0)
			max_EW--;
		
		if(max_EW==0)
		{
			isEW = false;
			max_EW = MAX_CAPACITY;
		}
		
		freeCrossR--;
		
		printCrossroadState();
		
		r.leave();
	}

	public void exitCrossroad()
	{
		r.enterWhen();
		
		freeCrossR++;
		doneCars++;
		
		printCrossroadState();
		
		r.leave();
	}
}
