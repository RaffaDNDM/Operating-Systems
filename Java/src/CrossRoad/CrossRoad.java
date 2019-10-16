package CrossRoad;

public abstract class CrossRoad 
{
	/*
	 * Si modelli un incrocio N-S ed E-W di due strade a senso unico. L'incrocio ha
	 * una capienza di max 3 veicoli nella stessa direzione (N-S oppure E-W), mentre
	 * non vi possono essere nell'incrocio veicoli su direzioni diverse. Si definisca
	 * il problema di sincronizzazione con una Rete di Petri, cercando di risolvere
	 * un'eventuale problema di starvation. Si realizzi il sistema utilizzando i semafori
	 * con una classe includente un metodo di collaudo.
	 */
	
	
	
	private static final String TITLE ="   ______                                          __\n"+
									   "  / ____/________  ___________________  ____ _____/ /\n"+
									   " / /   / ___/ __ \\/ ___/ ___/ ___/ __ \\/ __ \\`/ __  / \n"+
									   "/ /___/ /  / /_/ (__  |__  ) /  / /_/ / /_/ / /_/ /  \n"+
									   "\\____/_/   \\____/____/____/_/   \\____/\\__,_/\\__,_/   \n";
	
	protected String type_app="";
	public static final int MAX_CAPACITY=3;
	protected int max_EW = MAX_CAPACITY;
	protected int max_NS = max_EW;
	protected int freeCrossR=MAX_CAPACITY; 
	protected int waitNS = 0;
	protected int waitEW = 0;
	protected int doneCars = 0;
	protected boolean isEW = false;
	
	public abstract void enterCrossroadNS();
	public abstract void enterCrossroadEW();
	public abstract void exitCrossroad();
	
	public void title()
	{
		System.out.println("------------------------------------------------------------");
		System.out.println(TITLE);
		System.out.println(type_app);
		System.out.println("------------------------------------------------------------");
	}

	public void printCrossroadState()
	{
		System.out.println("  NUmber of cars         :   "+(MAX_CAPACITY-freeCrossR));
		System.out.println("  Number of crossed cars :   "+doneCars);
		System.out.println("-------------------------------------------------------------");
		System.out.println("  Waiting cars in direction NS:   "+waitNS);
		System.out.println("  Waiting cars in direction EW:   "+waitEW);
		System.out.println("-------------------------------------------------------------");
	}
}
