/**
   @author Di Nardo Di Maio Raffaele

   Realizzare un gestore di lock secondo il protocollo lettori/scrittori.
   A tale scopo si prevedano metodi eventualmente sospensivi per:
   - chiedere un lock esclusivo
   (un solo thread alla volta puv= ottenerlo e mantenerlo per un certo tempo)
   - chiedere un lock condiviso
   (pi� thread possono ottenere questo tipo di lock)
   - rilasciare un lock esclusivo
   - rilaciare un lock condiviso
   - interrogare sullo stato globale (libero, lock esclusivo assegnato,
   numero di lock condivisi assegnati)
   Affrontare eventuali problemi di starvation.
*/

package ReadersWriters;

public abstract class ReadersWriters
{
   private final static String LINE = "----------------------------------------------";
   protected boolean free = true;
   protected int num_shared = 0;
   protected int enqueued_shared = 0;
   protected boolean exclusive = true;
   protected int enqueued_exclusive = 0;
   protected int served_shared = 0;
   protected int served_exclusive = 0;

   /**
      Request of an exclusive lock
   */
   public abstract void exclusive_lock();

   /**
      Request of a shared lock
   */
   public abstract void shared_lock();

   /**
      Release of the exclusive lock
   */
   public abstract void exclusive_unlock();

   /**
      Release of the shared lock
   */
   public abstract void shared_unlock();

   /**
      Evaluate if the exclusive lock was already assigned
      @return false if assigned true otherwise
   */
   public boolean exclusive()
   {
      return exclusive;
   }

   /**
      Number of assigned shared locks
      @return number of assigned shared locks
   */
   public int num_assigned_Shared()
   {
      return num_shared;
   }

   /**
      Number of threads waiting for the assignment of the exclusive lock
      @return number of threads waiting for exclusive lock
   */
   public int num_exclusive()
   {
      return enqueued_exclusive;
   }

   /**
      Number of threads waiting for the assignment of a shared lock
      @return number of threads waiting for shared lock
   */
   public int num_shared()
   {
      return enqueued_shared;
   }

   /**
      Display the current state of the Readers-Writers system
      -> number of readers that are reading
      -> exclusive lock = 1 if there is a writer that owns it
      -> number of users waiting for their service
      -> number of users that complete their services
   */
   public void print_state()
   {
      System.out.println(LINE);
      System.out.printf("   Shared_locks: %2d     Exclusive_lock:  %d \n", num_shared, exclusive?0:1);
      System.out.printf("Enqueued_shared: %2d Enqueued_exclusive: %2d \n", enqueued_shared, enqueued_exclusive);
      System.out.printf("  Served_shared: %2d   Served_exclusive: %2d \n", served_shared, served_exclusive);
      System.out.println(LINE);
   }
}
