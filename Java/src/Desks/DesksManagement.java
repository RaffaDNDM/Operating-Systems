package Desks;

import java.util.Scanner;

import os.Util;

public class DesksManagement
{
	public static final int CLIENTS = 20;
	
	public static void main (String args[]) 
	{
		System.out.println("Scegliere il numero di sportelli");
		Scanner input = new Scanner(System.in);
		
		int desks = Integer.parseInt(input.nextLine());
		
		System.out.println("Scegliere il tipo di programma da eseuire");
		System.out.println("1. Monitor di Hoare");
		System.out.println("2. Monitor di Java");
		System.out.println("3. Regione critica");
		System.out.println("4. Semaforo numerico");
		
		Desks d=null;
		
		int choice = Integer.parseInt(input.nextLine());
		
		switch(choice)
		{
			case 1:
				d = new DesksMonitor(desks);
				break;
			case 2:
				d = new DesksJava(desks);
				break;
			case 3:
				d = new DesksRegion(desks);
				break;
			case 4:
				d = new DesksSemaphore(desks);
				break;
			default:
				System.exit(1);
		}
		
		for(int i=0; i<CLIENTS; i++)
		{
			new Client(i, d).start();
			Util.rsleep(250,1000);
		}
	}
}

