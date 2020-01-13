package CarWashing;

public abstract class CarWashing
{
   protected final static String LINE = "---------------------------------------------";
   protected final static int CARS = 20;
   protected int PLACES_A = 8;
   protected int PLACES_B = 4;
   protected int freeA = PLACES_A;
   protected int freeB = PLACES_B;
   protected int wait_tot = 0;
   protected int wait_par = 0;
   protected int done_tot = 0;
   protected int done_par = 0;

   /**********LAVAGGIO ESTERNI***********/
   public abstract void prenotaParziale();
   public abstract void pagaParziale();
   
   /**********LAVAGGIO INTERNI***********/
   public abstract void prenotaTotale();
   public abstract void lavaInterno();
   public abstract void pagaTotale();
   
   public void stampaSituazioneLavaggio()
   {
      System.out.println(LINE);
      System.out.println("Clienti in attesa di lavaggio totale: "+wait_tot);
      System.out.println("Clienti in attesa di lavaggio parziale: "+wait_par);
      System.out.println("Clienti che hanno pagato il lavaggio totale: "+done_tot);
      System.out.println("Clienti che hanno pagato il lavaggio parziale: "+done_par);
   }
}