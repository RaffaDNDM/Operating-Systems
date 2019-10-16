package Fontebella;

public abstract class Fontebella
{
   protected final static int CLIENTI = 20;
   protected int clientiA = 0, clientiB = 0; // clienti nelle rispettive code
   protected int serviti = 0; // ultimo zampillo occupato (0..7)
   protected int stat = 2; // conteggio per priorita` clienti B
   protected int zampilliLiberi = 8; // i clienti in fontana saranno 8-zampilliLiberi
   protected int ultimoZampillo = 7; // ultimo zampillo occupato (0..7)  
   
   public abstract int entraCodaA();
   public abstract int entraCodaB();
   public abstract void fineRiempimento();

   public void stampaSituazione()
   {
      System.out.println("-----------------------------------------------");
      System.out.println("Clienti in A: "+clientiA);
      System.out.println("Clienti in B: "+clientiB);
      System.out.println("Clienti serviti: "+serviti);
   }
}