package DropOff;

public abstract class DropOff
{
	private final static int DESKS = 2;
	
	protected int punLib = DESKS;
	protected boolean[] pLib = new boolean[DESKS];
	protected boolean libCD = true;
	
	protected int wprio = 0;
	protected int wnorm = 0;
	
	public abstract int inCoda(boolean prio);
	public abstract void regok(int pos);
	public abstract void term(int pos);
}
