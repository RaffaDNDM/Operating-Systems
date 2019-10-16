package Desks;

public class DesksJava extends Desks
{
	private int num_freeSport;
	private int num_sport;
	private int ticketQueue = 0;
	private int service = 0;
	
	
	private boolean free[];
	
	public DesksJava(int num_sport) 
	{
		this.num_sport=num_sport;
		num_freeSport=num_sport;
		free = new boolean[num_sport];
		for(int i=0; i<num_sport; free[i++]=true);
	}
	
	public synchronized int enterQueue()
	{
		int ticket = ticketQueue++;
		
		while(num_freeSport==0 || service!=ticket)
		{
			try 
			{
				wait();
			} 
			catch (Exception e) 
			{
				System.out.println("bloccato in attesa");
			}
		}
		
		num_freeSport--;
		service++;
		
		int i;
		for(i=0; i< num_sport; i++) 
		{
			if(free[i])
				break;
		}
		
		free[i]=false;
		
		return i;
	}
	
	public synchronized void exitQueue(int sport)
	{
		free[sport]=true;
		num_freeSport++;
		notify();
	}
}
