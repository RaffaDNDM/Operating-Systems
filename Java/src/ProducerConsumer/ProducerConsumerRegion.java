package ProducerConsumer;
import os.Region;
import os.RegionCondition;

public class ProducerConsumerRegion extends ProducerConsumer
{
	private Region r = new Region(0); 
	
	
	public int read(int num_read)
	{
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return num_occupied!=0;
			}	
		});
		
		while(num_occupied>0 && num_read>0)
		{
			num_occupied--;
			num_read++;
		}
		
		if(num_read==0)
		{
			num_read_done++;
		}
		
		state();
		r.leave();
		return num_read;
	}

	public int write(int num_write)
	{
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return num_free!=0;
			}	
		});
		
		while(num_free>0 && num_write>0)
		{
			num_free--;
			num_write++;
		}
		
		if(num_write==0)
		{
			num_write_done++;
		}
		
		state();
		r.leave();
		return num_write;
	}

}
