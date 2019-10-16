package Desks;

public abstract class Desks
{
   protected final static int DESKS = 4;
   protected final static int CLIENTS = 20;
   protected int freeDesks = DESKS;
   protected boolean[] desks = new boolean[DESKS];
   protected int wait_clients = 0;
   protected int done_clients = 0;
   

   public abstract int entraCoda();
   public abstract int esce(int sport);
   
}