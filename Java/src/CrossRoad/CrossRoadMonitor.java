package CrossRoad;

import os.Monitor;

public class CrossRoadMonitor extends CrossRoad 
{
	public static final int CARS=20;
	private Monitor m = new Monitor();
	private Monitor.Condition directionNS = m.new Condition();
	private Monitor.Condition directionEW = m.new Condition();
	
	public CrossRoadMonitor()
	{
		type_app="                  MONITOR DI HOARE";
	}
	
	public void enterCrossroadNS()
	{
		m.mEnter();
		
		waitNS++;
		
		printCrossroadState();
		
		if(freeCrossR==0 || (waitEW>0 && isEW))
		{
			directionNS.cWait();
		}
		
		waitNS--;
		
		if(waitEW>0)
		{
			max_NS--;
		}
		
		freeCrossR--;
		
		printCrossroadState();
		
		if(max_NS==0)
		{
			isEW=true;
			max_NS=MAX_CAPACITY;
		}
		
		m.mExit();
	}

	public void enterCrossroadEW()
	{
		m.mEnter();
		
		waitEW++;
		
		printCrossroadState();
		
		if(freeCrossR==0 || (waitNS>0 && !isEW))
		{
			directionEW.cWait();
		}
		
		waitEW--;
		freeCrossR--;
		
		printCrossroadState();
		
		if(waitNS>0)
		{
			max_EW--;
		}
		
		if(max_EW==0)
		{
			isEW=false;
			max_EW=MAX_CAPACITY;
		}
		
		m.mExit();
	}
	
	public void exitCrossroad()
	{
		m.mEnter();
		
		doneCars++;
		freeCrossR++;
		
		printCrossroadState();
		
		if(waitNS>0 && !isEW)
			directionNS.cSignal();
		else if(waitEW>0 && isEW)
			directionEW.cSignal();
		else if(waitNS>0)
			directionNS.cSignal();
		else if(waitEW>0)
			directionEW.cSignal();
		
		m.mExit();
	}	
}
