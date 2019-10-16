package DropOff;

import java.util.Scanner;

import os.Util;

public class DropOffMain
{
	public static void main (String args[]) 
	{
		System.out.println("Scegliere il numero di viaggiatori");
		Scanner input = new Scanner(System.in);
		
		int viaggiatori = Integer.parseInt(input.nextLine());
		
		System.out.println("Scegliere il tipo di programma da eseuire");
		System.out.println("1. Monitor di Hoare");
		System.out.println("2. Monitor di Java");
		System.out.println("3. Regione critica");
		System.out.println("4. Semaforo numerico");
		
		DropOff d = null;
		
		int choice = Integer.parseInt(input.nextLine());
		
		switch(choice)
		{
			case 1:
				d = new DropOffMonitor();
				break;
			case 2:
				d = new DropOffJava();
				break;
			case 3:
				d = new DropOffRegion();
				break;
			case 4:
				d = new DropOffSemaphore();
				break;
			default:
				System.exit(1);
		}
			
		for(int i=0; i<viaggiatori; i++)
		{
			new Traveller(i,d).start();
			Util.rsleep(250, 1000);
		}
	}
}
