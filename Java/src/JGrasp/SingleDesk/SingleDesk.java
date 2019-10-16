/**
* Modellare l'attesa ad uno sportello unico con il Monitor di Java
* ma con una attesa ordinata. A tale scopo si modelli la prenotazione
* con prelievo di un ticket numerato progressivo (si pensi a talune
* attuali sale d'attesa di uffici pubblici). Prevedere anche un ticket
* urgente che ha priorita'  su quello normale, assumendo che i clienti
* urgenti siano sporadici. Il servizio allo sportello venga modellato con
* un'opportuna attesa casuale. Prevedere anche brevi intervalli di tempo
* casuali durante i quali lo sportello e' fuori servizio, nonche' la
* possibilita' che il cliente precisi un timeout (massima attesa in coda).
*/

package SingleDesk;

public abstract class SingleDesk
{
   protected final static int CLIENTS = 20;
   protected boolean free = true;
   protected int wait_prio = 0;
   protected int wait_norm = 0;
   protected int done_prio = 0;
   protected int done_norm = 0;
   protected int suspended_prio = 0;
   protected int suspended_norm = 0;

   public abstract boolean entra(boolean priority);
   public abstract void esce(boolean priority, boolean noStufo);
}