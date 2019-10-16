package CrossRoad;

import os.Semaphore;

public class CrossRoadSemaphore extends CrossRoad 
{
	private Semaphore m = new Semaphore(true);
	private Semaphore directionNS = new Semaphore(false);
	private Semaphore directionEW = new Semaphore(false);
	
	public static final int CARS=20;
	
	public CrossRoadSemaphore()
	{
		type_app="            SEMAFORO PRIVATO";
	}
	
	public void enterCrossroadNS()
	{
		m.p();
		
		waitNS++;
		
		printCrossroadState();
		
		if((isEW && waitEW>0) || freeCrossR==0)
		{
			m.v();
			directionNS.p();
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
	
		m.v();
	}

	public void enterCrossroadEW()
	{
		m.p();
		waitEW++;
		
		printCrossroadState();
		
		if((!isEW && waitNS>0) || freeCrossR==0)
		{
			m.v();
			directionEW.p();
		}
		
		waitEW--;
		
		if(waitNS>0)
		{
			max_EW--;
		}

		freeCrossR--;
		
		printCrossroadState();
		
		if(max_EW==0)
		{
			isEW=false;
			max_EW=MAX_CAPACITY;
		}
		
		m.v();
	}
	
	public void exitCrossroad()
	{
		m.p();
		
		freeCrossR++;
		doneCars++;
		
		printCrossroadState();
		
		if(waitNS>0 && !isEW)
			directionNS.v();
		else if(waitEW>0 && isEW)
			directionEW.v();
		//Flush of the buffer of enqueued cars in the last iteractions
		else if(waitNS>0)
			directionNS.v();
		else if(waitEW>0)
			directionEW.v();
		else
			m.v();
			
	}
}
