package Spinlock;

public abstract class NumberSemaphore 
{
	/*
	 * Realizzare la classe Spinlock che implementa il semaforo numerico in una forma
	 * con parziale busy-waiting. Si forniscano i metodi p(), p(long timeout), v()
	 * come nella classe Semaphore. Il thread che deve attendere si spospende con uno
	 * sleep per un tempo variabile prima di rivalutare la condizione sul semaforo. 
	 * Per ridurre il busy waiting tale tempo vale, nell'ordine, 8*T, 4*T, 2*T, 1*T, 
	 * 8*T, 4*T ecc. ciclicamente finchè l'attesa termina con la condizione verificata. 
	 * T sia una costante temporale adeguatamente impostata.
	*/
	
	public abstract void p();
	public abstract void p(long timeout); 
	public abstract void v();
}
