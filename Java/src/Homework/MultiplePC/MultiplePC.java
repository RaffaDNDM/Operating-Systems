/**
   @author Di Nardo Di Maio Raffaele

   Modificare l'esempio di Produttore/Consumatore con buffer multiplo
   e semafori in modo che il produttore(consumatore) possa scrivere(leggere) pi� elementi
   in una sola operazione. Gestire nelle chiamate un flag che, a causa di
   limitazioni di spazio del buffer, autorizzi da parte del thread chiamante
   la scrittura nel buffer di una parte degli elementi prodotti
   (in questo caso la chiamata deve restituire quanti elementi
   sono stati effettivamente scritti), oppure il consumo di una
   parte della richiesta (gli elementi che ci sono in quel momento nel buffer,
   anche 0). Al posto del flag si possono definire pi� chiamate overloaded.
*/

package MultiplePC;

public abstract class MultiplePC
{
   //size of the buffer
   protected int size = 0;
   //number of free slots in the buffer
   protected int free;
   //number of enqueued reading requests
   protected int enqueued_read = 0;
   //number of served reading requests throught partial readings
   protected int served_p_read = 0;
   //number of served reading requests with no partial readings
   protected int served_read = 0;
   //number of enqueued writing requests
   protected int enqueued_write = 0;
   //number of served writing requests throught partial writings
   protected int served_p_write = 0;
   //number of served writing requests with no partial writings
   protected int served_write = 0;

   /**
      Request of reading an amount of data
      @param num number of data the thread would like to read from the buffer
      @param partial true if the thread can satisfy also a part of its request
      @return num of read elements by the thread
   */
   public abstract int read(int num, boolean partial);

   /**
      Request of writing an amount of data
      @param num number of data the thread would like to write in the buffer
      @param partial true if the thread can satisfy also a part of its request
      @return num of read elements by the thread
   */
   public abstract int write(int num, boolean partial);

   /**
      Return the index of the writing semaphore/condition on which there are waiting
      thread compatible with the actual number of full slots in the buffer
      @param index of the writing semaphore/condition
   */
   public abstract int max_write();

   /**
      Return the index of the reading semaphore/condition on which there are waiting
      thread compatible with the actual number of full slots in the buffer
      @param index of the reading semaphore/condition
   */
   public abstract int max_read();

   /**
      Size of the buffer of the system
      @return size of the buffer
   */
   public int getSize()
   {
      return size;
   }
}
