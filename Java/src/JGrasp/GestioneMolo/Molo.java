package GestioneMolo;

public abstract class Molo
{  
   protected int truckD = 0; 
   protected int truckS = 0;
   protected final static int NUM_CAMION = 20;
   protected boolean[] puntoDiCarico = new boolean[3];
   
   public abstract int entra(boolean richiestaA);
   public abstract void esce(int punto);
}
