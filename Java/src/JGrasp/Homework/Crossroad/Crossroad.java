/**
   @author Di Nardo Di Maio Raffaele 1204879   

   Si modelli un incrocio N-S ed E-W di due strade a senso unico. L'incrocio ha
   una capienza di max 3 veicoli nella stessa direzione (N-S oppure E-W), mentre
   non vi possono essere nell'incrocio veicoli su direzioni diverse. Si definisca
   il problema di sincronizzazione con una Rete di Petri, cercando di risolvere
   un'eventuale problema di starvation.
*/
package Crossroad;

public abstract class Crossroad
{
   //max capacity of the crossroad
   protected final static int MAX_CAPACITY = 3;
   //max number of cars of the same time that can cross continuously the crossroad
   protected final static int MAX_ON_DIR = 4;
   
   //free places of the crossroad in this moment
   protected int capacity = MAX_CAPACITY;
   //number of cars of the same type that could cross the crossroad in the future
   protected int on_dir = MAX_ON_DIR;
   //numbers of cars waiting in the arrival queue of NS cars
   protected int enqueued_NS = 0;
   //numbers of cars waiting in the arrival queue of EW cars
   protected int enqueued_EW = 0;
   //number of cars that crossed the crossroad
   protected int served = 0;
   //true if it's the NS cars turn
   protected boolean isNS = true;
   //number of cars that are crossing and are in the center of the crossroad
   protected int in_service = 0;

   /**
      Arrival to the crossroad from North and waiting condition
   */
   public abstract void enterNS();
   
   /**
      Arrival to the crossroad from East and waiting condition
   */
   public abstract void enterEW();
   
   /**
      Exit from the crossroad
   */
   public abstract void exit();
   
   /**
      Print the state of the crossroad in this moment showing
      number of cars enqueued
      number of cars that occupy the crossroad
      number of cars that crossed
   */
   public void print_state()
   {
      System.out.println("----------------------------------------------------------------");
      System.out.printf("| Enqueued cars on NS: %2d    Number of cars in crossroad: %2d |\n", enqueued_NS, MAX_CAPACITY-capacity);
      System.out.printf("| Enqueued cars on EW: %2d                    Served cars: %2d |\n", enqueued_EW, served);
   }
}