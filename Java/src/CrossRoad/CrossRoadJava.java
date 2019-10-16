package CrossRoad;

public class CrossRoadJava extends CrossRoad 
{	
	private int ticketNS = 0;
	private int ticketEW = 0;
	private int serviceNS = 0;
	private int serviceEW = 0;
	
	public CrossRoadJava()
	{
		type_app="             MONITOR DI JAVA";
	}
	
	public synchronized void enterCrossroadNS()
	{
		int ticket = ticketNS++;
		waitNS++;
		
		printCrossroadState();
		
		while(ticket!=serviceNS || freeCrossR==0 || (waitEW>0 && isEW))
		{
			try
			{
				wait();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		waitNS--;
		freeCrossR--;
		serviceNS++;
		
		if(waitEW>0)
			max_NS--;
		
		if(max_NS==0)
		{
			isEW = true;
			max_NS = MAX_CAPACITY;
		}
		
		printCrossroadState();
	}

	public synchronized void enterCrossroadEW()
	{
		int ticket = ticketEW++;
		waitEW++;
		
		printCrossroadState();
		
		while(ticket!=serviceEW || freeCrossR==0 || (waitNS>0 && !isEW))
		{
			try
			{
				wait();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		waitEW--;
		freeCrossR--;
		serviceEW++;
		
		if(waitNS>0)
			max_EW--;
		
		if(max_EW==0)
		{
			isEW = false;
			max_EW = MAX_CAPACITY;
		}
		
		printCrossroadState();
	}
	
	public synchronized void exitCrossroad()
	{
		freeCrossR++;
		doneCars++;
		
		printCrossroadState();
		
		notifyAll();
	}
}
