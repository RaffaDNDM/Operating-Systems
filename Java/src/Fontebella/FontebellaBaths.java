package Fontebella;

public abstract class FontebellaBaths
{
	public static final int LATE=7;
	public Pair[] indices = {new Pair(0,2), new Pair(0,4), new Pair(2, 6), new Pair(4,6), new Pair(6,4), new Pair(6,2), new Pair(4,0), new Pair(2,0)};
	public String[][] matrix = new String[LATE][LATE];
	
	public static final String TITLE="    ______            __       __         ____      \n"+     
	                                 "   / ____/___  ____  / /____  / /_  ___  / / /___ _ \n"+
	                                 "  / /_  / __ \\/ __ \\/ __/ _ \\/ __ \\/ _ \\/ / / __ '/ \n"+
	                                 " / __/ / /_/ / / / / /_/  __/ /_/ /  __/ / / /_/ /  \n"+
	                                 "/_/    \\____/_/ /_/\\__/\\___/_.___/\\___/_/_/\\__,_/   \n";
	
	protected static final int NUM_SPOUTS = 8;
	protected int freeSpouts = NUM_SPOUTS;
	protected int spout = 0;
	protected int priority=2;
	
	protected int waitA=0;
	protected int waitB=0;
	protected int done_clients=0;
	protected String type_app = "";
	
	public abstract int enterA();
	public abstract int enterB();
	public abstract void endFill();
	
	protected void title()
	{
		System.out.println("-----------------------------------------------------------------------");
		System.out.println(TITLE);
		System.out.println(type_app);
		System.out.println("-----------------------------------------------------------------------");
	}
	
	protected void initMatrix()
	{
		for(int i=0; i<matrix.length; i++)
        {
        	for(int j=0; j<matrix[0].length; j++)
        	{
        		matrix[i][j]="  ";
        	}
        }
		
		for(int i=0; i<NUM_SPOUTS; i++)
		{
			Pair p = indices[i]; 
			matrix[p.getRow()][p.getColumn()]="O";
		}
	}
	
	protected void printFontebellaState()
	{	
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Free spouts: "+freeSpouts);
		System.out.println("-----------------------------------------------------------------------");
		System.out.print("\n");
		
		for(int i=0; i<NUM_SPOUTS; i++)
		{
			Pair p = indices[i]; 
			matrix[p.getRow()][p.getColumn()]="O";
		}
		
		int occupied = NUM_SPOUTS-freeSpouts; 
		for(int i=spout-1; i>=0 && occupied>0; i--)
		{
			Pair p = indices[i];
			matrix[p.getRow()][p.getColumn()]="X";
			occupied--;
		}
		
		for(int i=NUM_SPOUTS-1; i>=0 && occupied>0; i--)
		{
			Pair p = indices[i];
			matrix[p.getRow()][p.getColumn()]="X";
			occupied--;
		}
		
		for(int j=0; j<LATE; j++)//riga
		{
			for(int i=0; i<LATE; i++)//colonna
			{
				System.out.print(matrix[j][i]);
			}
			System.out.print("\n");
		}
		
		System.out.print("\n");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Waiting A clients: "+waitA);
		System.out.println("Waiting B clients: "+waitB);
		System.out.println("Completed clients: "+done_clients);
	}
	
	private class Pair
	{
		private int i;
		private int j;
		
		public Pair(int i, int j)
		{
			this.i=i;
			this.j=j;
		}
		
		public int getRow()
		{
			return i;
		}
		
		public int getColumn()
		{
			return j;
		}
	}
	
}
