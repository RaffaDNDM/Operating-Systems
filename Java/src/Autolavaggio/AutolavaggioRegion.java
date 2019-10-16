package Autolavaggio;

import os.Region;
import os.RegionCondition;

public class AutolavaggioRegion extends Autolavaggio
{
	private Region r;
	
	public AutolavaggioRegion()
	{
		type_app = "                         REGIONE CRITICA";
		r = new Region(0);
	}
	
	public void prenotaParziale()
	{
		r.enterWhen();
		partial_wait++;
		stampaSituazioneLavaggio();
		r.leave();
		
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return free_A!=0 && !(total_wait>0 && total_size<NUM_PLACES_B);
			}
		});
		
		partial_wait--;
		free_A--;
		partial_size++;
		
		stampaSituazioneLavaggio();
		r.leave();
	}

	public void pagaParziale()
	{
		r.enterWhen();
		
		partial_size--;
		free_A++;
		partial_done++;
		
		stampaSituazioneLavaggio();
	
		r.leave();
	}

	public void prenotaTotale()
	{
		r.enterWhen();
		total_wait++;
		stampaSituazioneLavaggio();
		r.leave();
		
		r.enterWhen(new RegionCondition()
		{
			public boolean evaluate()
			{
				return free_A!=0 &&  total_size<NUM_PLACES_B;
			}
		});
		
		total_wait--;
		free_A--;
		total_size++;
		
		stampaSituazioneLavaggio();
		
		r.leave();
	}

	public void lavaInterno()
	{
		r.enterWhen();
		free_A++;
		free_B--;
		
		stampaSituazioneLavaggio();
		
		r.leave();
	}

	public void pagaTotale()
	{
		r.enterWhen();
		free_B++;
		total_size--;
		total_done++;
		
		stampaSituazioneLavaggio();
		
		r.leave();
	}
}
