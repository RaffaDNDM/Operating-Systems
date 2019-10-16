package Autolavaggio;

public abstract class Autolavaggio
{
	public static final int NUM_PLACES_A = 8;
	public static final int NUM_PLACES_B = 4;
	public static final String TITLE="     ______              _       __           __   \n"+
			                         "    / ____/___ ______   | |     / /___ ______/ /_  \n"+
			                         "   / /   / __ `/ ___/   | | /| / / __ `/ ___/ __ \\ \n"+
			                         "  / /___/ /_/ / /       | |/ |/ / /_/ (__  ) / / / \n"+
			                         "  \\____/\\__,_/_/        |__/|__/\\__,_/____/_/ /_/  \n"+
			                         "                                                   \n";
			                         
	
	protected int partial_size=0; //numero complessivo di coloro che hanno già prenotato A
	protected int total_size=0; //numero complessivo di coloro che stanno effettuando il servizio in B
	protected int free_A=NUM_PLACES_A;
	protected int free_B=NUM_PLACES_B;

	protected int total_wait=0;
	protected int partial_wait=0;
	protected int total_done=0;
	protected int partial_done=0;
	protected String type_app = "";

	public abstract void prenotaParziale();
	public abstract void pagaParziale();
	
	public abstract void prenotaTotale();
	public abstract void lavaInterno();
	public abstract void pagaTotale();

	public void title()
	{
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(TITLE);
		System.out.println(type_app);
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("---- LAVAGGIO:  Parziali = "+partial_size+" Totali = "+total_size+" ----");
	}
	
	protected void stampaSituazioneLavaggio()
    {   
        System.out.print("Situazione ZONA A: ");
        for(int p=0;p<NUM_PLACES_A;p++)
            if(p<(NUM_PLACES_A-free_A))
                System.out.print("X");
            else
                System.out.print("O");
        
        System.out.println("");
        System.out.print("Situazione ZONA B: ");
        for(int q=0;q<NUM_PLACES_B;q++)
            if(q<(NUM_PLACES_B-free_B))
                System.out.print("X");
            else
                System.out.print("O");
        
        System.out.println("");
        System.out.println("Situazione ZONA di PRENOTAZIONE: ");
        System.out.print("-");
        for(int e=0;e<total_wait;e++)
            System.out.print("T");
        
        System.out.println("");
        System.out.print("-");
        for(int r=0;r<partial_wait;r++)
            System.out.print("P");
        
        System.out.println("");
        System.out.println("LAVATI:  Parziali = "+partial_done+" Totali = "+total_done);
    }

}
