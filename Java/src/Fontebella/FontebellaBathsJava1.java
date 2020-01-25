/**
@author Di Nardo Di Maio Raffaele
*/

package Fontebella;
import java.util.concurrent.CountDownLatch;

import os.Util;

public class FontebellaBathsJava {

	private static final long FULL_TIME=15500L;
	private int countA=0,countB=0;
	private int priority=2;
	private int free_spouts=8;
	private int last_spout=7;
	private int ticketA=0,ticketB=0;
	private int serviceA=0, serviceB=0;

	private class ClientA extends Thread {

		public ClientA(String name) {
			super(name);
		}

		public void run() {
			System.out.println("!!! Il cliente "+getName()+" di tipo A va in coda");
            int zamp=enterA();
            System.out.println("+++ Il cliente "+getName()+" di tipo A va a bere allo zampillo "+zamp);
            Util.sleep(FULL_TIME);
            System.out.println("--- Il cliente "+getName()+" di tipo A lascia lo zampillo "+zamp);
            finishFull();
		}

	}

	private class ClientB extends Thread {

		public ClientB(String name) {
			super(name);
		}

		public void run() {
			System.out.println("!!! Il cliente "+getName()+" di tipo A va in coda");
            int zamp=enterB();
            System.out.println("+++ Il cliente "+getName()+" di tipo A va a bere allo zampillo "+zamp);
            Util.sleep(FULL_TIME);
            System.out.println("--- Il cliente "+getName()+" di tipo A lascia lo zampillo "+zamp);
            finishFull();
		}
	}

	public synchronized int enterA() {
		countA++;
		System.out.println("vvv Il cliente "+Thread.currentThread().getName()+" di tipo A attende in coda (clientiA="+countA+")");
		int ticket = ticketA++;

		while(free_spouts==0 || (countB>0 && priority!=0) || serviceA != ticket) {
			try {
				wait();
			}
			catch (InterruptedException e) {};
		}

		countA--;
		System.out.println("^^^ Il cliente "+Thread.currentThread().getName()+" di tipo A termina l'attesa in coda (clientiA="+countA+")");

		free_spouts--;
		priority=2;
		System.out.println("************ zampilli liberi = "+last_spout);
		serviceA++;

		return (last_spout = (last_spout+1)%8)+1;
	}

	public synchronized int enterB() {
		countB++;

		System.out.println("vvv Il cliente "+Thread.currentThread().getName()+" di tipo B attende in coda (clientiB="+countB+")");
        int ticket = ticketB++;

        while(free_spouts==0 || (countA>0 && priority==0) || serviceB != ticket) {
        	try {
        		wait();
        	}
        	catch (InterruptedException e) {};
        }

        countB--;
        System.out.println("^^^ Il cliente "+Thread.currentThread().getName()+" di tipo B termina l'attesa in coda (clientiB="+countB+")");

        free_spouts--;
        System.out.println("************ zampilli liberi = "+free_spouts);
        if (countA>0)
            priority--;  // conteggio specifico
        serviceB++;

        return (last_spout = (last_spout+1)%8)+1;
	}

	public synchronized void finishFull() {
		free_spouts++;
		System.out.println("************ zampilli liberi = "+free_spouts);
		notifyAll();
	}

	public static void main(String[] args) {
		System.err.println("** Battere Ctrl-C per terminare!");
        FontebellaBathsJava fontain = new FontebellaBathsJava();

        int cnt=0;
        for(;cnt<40;cnt++)
        {
            Util.rsleep(500, 2000);
            if (Util.randVal(1,2) == 1)
                fontain.new ClientA("num"+(cnt)).start();
            else
                fontain.new ClientB("num"+(cnt)).start();
        }
	}
}
