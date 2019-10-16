package CrossRoad;

import java.util.Scanner;
import os.Util;

public class CrossRoadManagement
{
	public static void main(String[]  args)
	{	
		System.out.println("Scegliere il numero di automobili");
		Scanner input = new Scanner(System.in);
		
		int num_cars = Integer.parseInt(input.nextLine());
		
		System.out.println("Scegliere il tipo di programma da eseuire");
		System.out.println("1. Monitor di Hoare");
		System.out.println("2. Monitor di Java");
		System.out.println("3. Regione critica");
		System.out.println("4. Semaforo numerico");
		
		CrossRoad cr = null;
		
		int choice = Integer.parseInt(input.nextLine());
		
		switch(choice)
		{
			case 1:
				cr = new CrossRoadMonitor();
				break;
			case 2:
				cr = new CrossRoadJava();
				break;
			case 3:
				cr = new CrossRoadRegion();
				break;
			case 4:
				cr = new CrossRoadSemaphore();
				break;
			default:
				System.exit(1);
		}
		
		cr.title();
		
		Util.sleep(4000);
		
		cr.printCrossroadState();
		
		for(int i=0; i<num_cars; i++)
		{
			int r = Util.randVal(1,10);
			
			if(r<4)
				new EWCar(i, cr).start();
			else
				new NSCar(i, cr).start();
			
			Util.rsleep(250, 1000);
		}
	}
}
