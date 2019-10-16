package CrossRoad;

import os.Util;

public class EWCar extends Thread
{
	private static final long MIN_TIME = 1000L;
	private static final long MAX_TIME = 8000L;
	private int car;
	private CrossRoad  c;
	
	public EWCar(int car, CrossRoad c)
	{
		this.car=car;
		this.c=c;
	}
	
	public void run()
	{
		System.out.println(car+" enqueued in EW direction");
		c.enterCrossroadEW();
		Util.rsleep(MIN_TIME, MAX_TIME);
		System.out.println(car+" go across the crossroad in EW direction");
		c.exitCrossroad();
	}
}