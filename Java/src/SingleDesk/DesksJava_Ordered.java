package SingleDesk;
import java.util.Random;
import os.Util;

public class DesksJava_Ordered implements DeskManagement
{
	//number of desks in the system
	private int num_desks;
	//number of free desks
	private int free_desks;
	//set of desks (true= free, false=occupied)
	private boolean free[];
	//number of normal clients in the queue
	private int num_ordinary;
	//number of clients with priority in the queue
	private int num_priority;
	//last ticket given to normal clients
	private int normal_ticket=0;
	//last ticket given to clients with priority
	private int priority_ticket=0;
	//next ticket of normal clients that is going to be served by system
	private int next_service_Norm=1;
	//next ticket of clients with priority that is going to be served by system
	private int next_service_Prio=1;
	
	/**
	 * Class that identifies the system with the queues and the desks
	 * @param num_desks number of desks in the system
	 */
	public DesksJava_Ordered(int num_desks) 
	{
		this.num_desks = free_desks = num_desks;
		this.num_ordinary=this.num_priority=0;
		
		//Initialization of status of desks
		free = new boolean[num_desks];
		for(int i=0; i<num_desks; free[i++]=true);
	}
	
	private class NormalClient extends Thread 
	{	
		//name of the client
		private String name;
		//min time of execution of its service routine
		private int min_time;
		//max time of execution of its service routine
		private int max_time;
		//time that a client waits at most in the queue
		private int timeout;
		
		/**
		 * Normal client in the system without timeout for the waiting time in the queue
		 * @param name name of the client
		 * @param min_time min time of execution of its service routine
		 * @param max_time max time of execution of its service routine
		 */
		public NormalClient(String name, int min_time, int max_time) 
		{
			this.name=name;
			this.min_time=min_time;
			this.max_time=max_time;
			this.timeout=-1;
		}
		
		/**
		 * Normal client in the system without timeout for the waiting time in the queue
		 * @param name name of the client
		 * @param min_time min time of execution of its service routine
		 * @param max_time max time of execution of its service routine
		 * @param timeout time that a client waits in the queue
		 */
		public NormalClient(String name, int min_time, int max_time, int timeout) 
		{
			this.name=name;
			this.min_time=min_time;
			this.max_time=max_time;
			this.timeout=timeout;
		}
		
		/**
		 * A client enters in the queue, completes its service routine and exits from desk
		 */
		public void run() 
		{
			System.out.println(name+" is waiting in the queue");
			
			//client enters in the queue
			int desk= enterNormalQueue(timeout);
			
			if(desk==-1)
				//Client overcomes its timeout and exits from queue
				System.out.println(name+" has no more patience and exits from the queue");
			else 
			{
				//Client is beginning its service routine
				System.out.println(name+" is served by desk "+desk);
				Util.rsleep(min_time, max_time);
				//Client completed its service routine
				exitQueue(desk);
				//Client exits from system
				System.out.println(name+" has finished its service routine at desk "+desk+" and exits from the queue");
			}
		}
		
	}
	
	/**
	 * A normal client enters in the queue of service
	 * @param timeout max time that a client waits in the queue
	 * @return number of desk in which the client is served
	 */
	public synchronized int enterNormalQueue(int timeout) 
	{
		//Assigns ticket to the client in normal clients queue
		normal_ticket++;
		//Increments the number of clients in normal clients queue
		num_ordinary++;
		
		long time_in=System.currentTimeMillis();
		
		try 
		{
			long spent_time=0;
			//If there are no free desks or it's not the turn of the client in normal clients
			//queue or there are enqueued clients with priority, the client needs to wait to be served
			while(free_desks==0 || normal_ticket!=next_service_Norm || num_priority!=0)
			{	
				if(timeout!=-1) 
				{
					spent_time= System.currentTimeMillis()-time_in;
					
					if(spent_time>=timeout)
						return -1;
				}
				
				wait();
			}
		}
		catch (InterruptedException e) 
		{
				e.printStackTrace();
		}
		//client is going to be served, so there will be a occupied desk
		//and the number of normal clients in queue will be decremented by a unit
		free_desks--;
		num_ordinary--;
		
		int desk=0;
		for(;desk<num_desks; desk++)
			if(free[desk])
				break;
		
		//the client goes to the first free desk and occupies it
		free[desk]=false;
		//the system can wait for the next enqueued normal client
		next_service_Norm++;
		
		return desk;
	}
	
	/**
	 * A client (normal or with priority) completes its service routine made at desk desk 
	 * @param desk number of desk in which the client completed its service routine
	 */
	public synchronized void exitQueue(int desk) 
	{
		free[desk]=true;
		free_desks++;
		notifyAll();
	}
	
	private class PriorityClient extends Thread 
	{
		
		//name of the client
		private String name;
		//min time of execution of its service routine
		private int min_time;
		//max time of execution of its service routine
		private int max_time;
		//time that a client waits at most in the queue
		private int timeout;
		
		/**
		 * Client with priority in the system without timeout for the waiting time in the queue
		 * @param name name of the client
		 * @param min_time min time of execution of its service routine
		 * @param max_time max time of execution of its service routine
		 */
		public PriorityClient(String name, int min_time, int max_time) 
		{
			this.name=name;
			this.min_time=min_time;
			this.max_time=max_time;
			this.timeout=-1;
		}
		
		/**
		 * Client with priority in the system without timeout for the waiting time in the queue
		 * @param name name of the client
		 * @param min_time min time of execution of its service routine
		 * @param max_time max time of execution of its service routine
		 * @param timeout time that a client waits in the queue
		 */
		public PriorityClient(String name, int min_time, int max_time, int timeout) 
		{
			this.name=name;
			this.min_time=min_time;
			this.max_time=max_time;
			this.timeout=timeout;
		}
		
		/**
		 * A client enters in the queue, completes its service routine and exits from desk
		 */
		public void run() 
		{
			System.out.println("[Priority] "+name+" is waiting in the priority queue");
			
			//client enters in the queue
			int desk= enterPriorityQueue(timeout);
			
			if(desk==-1)
				//Client overcomes its timeout and exits from queue
				System.out.println("[Priority]"+name+" has no more patience and exits from the queue");
			else 
			{
				//Client is beginning its service routine
				System.out.println("[Priority]"+name+" is served by desk "+desk);
				Util.rsleep(min_time, max_time);
				//Client completed its service routine
				exitQueue(desk);
				//Client exits from system
				System.out.println("[Priority]"+name+" has finished its service routine at desk "+desk+" and exits from the queue");
			}
		}
	}
	
	/**
	 * A client with priority enters in the queue of service
	 * @param timeout max time that a client waits in the queue
	 * @return number of desk in which the client is served
	 */
	public synchronized int enterPriorityQueue(int timeout) 
	{
		//Assigns ticket to the client in clients (with priority) queue
		priority_ticket++;
		//Increments the number of clients in clients (with priority) queue
		num_priority++;
		long time_in=System.currentTimeMillis();
		
		try 
		{
			long spent_time=0;
			//If there are no free desks or it's not the turn of the client
			//in clients (with priority) queue, the client needs to wait to be served
			while((free_desks==0 || priority_ticket!=next_service_Prio))
			{	
				if(timeout!=-1) 
				{
					spent_time= System.currentTimeMillis()-time_in;
					
					if(spent_time>=timeout)
						return -1;
				}
				
				wait();
			}
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//client is going to be served, so there will be a occupied desk
		//and the number of normal clients in queue will be decremented by a unit
		free_desks--;
		num_priority--;
		
		int desk=0;
		
		for(;desk<num_desks; desk++)
			if(free[desk])
		 		break;
		
		//the client goes to the first free desk and occupies it
		free[desk]=false;
		//the system can wait for the next enqueued client with priority
		next_service_Prio++;
		
		return desk;
	}
	
	private class AvailabilityThread extends Thread 
	{
		/**
		 * Class that manages the updating and the pauses of employees at desks
		 */
		public AvailabilityThread() 
		{
			super();
		}
		
		/**
		 * Management of closing and opening of desk in the system
		 */
		public void run() 
		{
			while(true) 
			{
				//Time between the end of the previous pause and the new one 
				Util.rsleep(10000,40000);
				//A desk is going in pause
				int desk = deskPause();
				System.out.println("The desk "+desk+" is in pause");
				//Random time in which the desk doesn't work
				Util.rsleep(3000,10000);
				System.out.println("The desk "+desk+" is turned back from pause");
				//The same desk is going to work again
				deskJob(desk);
			}
		}
	}
	
	/**
	 * A desk becomes working again
	 * @param desk number of desk you want to activate
	 */
	public synchronized void deskJob(int desk) 
	{
		//the desk becomes available again
		free[desk]=true;
		free_desks++;
	}

	/**
	 * A desk becomes not working for a while
	 * @return desk desk that will not work for a while
	 */
	public synchronized int deskPause() 
	{
		//to define the pause of a desk, this need to be no occupied
		while(free_desks==0);
		
		//Decrements number of available desks 
		free_desks--;
		
		int desk;
		for(desk=0; desk<num_desks; desk++)
			if(free[desk])
				break;
		
		//Selection of first desk available and not occupied
		//This desk becomes not available
		free[desk]=false;
		
		return desk;
	}

	/**
	 * Initialization and management of System and arrival of clients
	 */
	public static void main(String[] args) 
	{
		int num_desks=4;
		//Create the system of desks
		DesksJava_Ordered queue = new DesksJava_Ordered(num_desks);
		//Create the management of pause of desks worker
		queue.new AvailabilityThread().start();
		
		int count=0;
		Random random = new Random();
		
		//Arrival of clients
		while(true)
		{
			count++;
			Double probability= random.nextDouble();
			
			Util.rsleep(500, 2000);
			/*
			 * Arrival of new clients
			 * 90% normal clients 
			 *    = 45% normal clients with random timeout + 45% normal clients without timeout
			 * 10% clients with priority
			 *    = 45% clients with priority and random timeout + 45% clients with priority and without timeout
			 * 
			 * timeout is chosen randomly 
			 */
			if(probability<=0.45)
				queue.new NormalClient(count+"° client", 1000, 10000, 1000).start();
			else if (probability>0.45 && probability<=0.9)
				queue.new NormalClient(count+"° client", 1000, 10000).start();
			else if (probability>0.9 && probability<=0.95)
				queue.new PriorityClient(count+"° client", 1000, 10000,1000).start();
			else
				queue.new PriorityClient(count+"° client", 1000, 10000).start();
		}
	}
}

