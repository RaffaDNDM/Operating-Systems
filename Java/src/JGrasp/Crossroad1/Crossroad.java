/*
* Si modelli un incrocio N-S ed E-W di due strade a senso unico. L'incrocio ha
* una capienza di max 3 veicoli nella stessa direzione (N-S oppure E-W), mentre
* non vi possono essere nell'incrocio veicoli su direzioni diverse. Si definisca
* il problema di sincronizzazione con una Rete di Petri, cercando di risolvere
* un'eventuale problema di starvation.
*/

package Crossroad;

public abstract class Crossroad
{   
   protected final static int CARS = 20;
   protected boolean turnNS = true;
   protected int free =3;
   protected int max_dir = 5; // numero max di macchine che possono attraversare una direzione
   protected int done_NS = 0; // macchine già passate lungo la direzione NS
   protected int done_EW = 0; // macchine già passate lungo la direzione EW

   public abstract void entra(boolean NS);
   public abstract void esce(boolean NS);
   
   public void stampaSituazione()
   {
      System.out.println("---------------------------------------------");
      System.out.println("Numero di auto attraversate lungo EW: "+done_EW);
      System.out.println("Numero di auto attraversate lungo NS: "+done_NS);
   }
}