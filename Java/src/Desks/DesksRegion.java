package Desks;

import os.Region; 
import os.RegionCondition; 

public class DesksRegion extends Desks
{
	private int free_desks;
	private int num_desks;
	private boolean free[];
	private Region r = new Region(0);
	
	public DesksRegion(int num_desks) 
	{
		this.num_desks=free_desks=num_desks;
		free= new boolean[num_desks];
		for(int i=0; i<num_desks; free[i++]=true);
	}
	
	public int enterQueue() 
	{
		r.enterWhen(new RegionCondition() 
		{		
			public boolean evaluate() 
			{
				return free_desks!=0;
			}
		});
		
		free_desks--;
		int i;
		
		for(i=0; i<num_desks; i++)
		{
			if(free[i]==true)
				break;
		}
	
		free[i]=false;
		r.leave();
	
		return i;
	}
	
	public void exitQueue(int desk) 
	{
		r.enterWhen();
		free[desk]=true;
		free_desks++;
		r.leave();
		
	}
}
