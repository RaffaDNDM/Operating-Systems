package Airport;

public abstract class TorreDiControllo
{
	private static final String TITLE = "    ___    _                       __ \n"+
								   "   /   |  (_)________  ____  _____/ /_\n"+
								   "  / /| | / / ___/ __ \\/ __ \\/ ___/ __/\n"+
								   " / ___ |/ / /  / /_/ / /_/ / /  / /_  \n"+
								   "/_/  |_/_/_/  / .___/\\____/_/   \\__/  \n"+
								   "             /_/                      \n";
	                      							     
	public static final int POSTI_A = 2;
	public static final int POSTI_B = 2;
	protected int liberiA = POSTI_A;
	protected int liberiB = POSTI_B;
	protected int attesaPista = 0;
	protected int attesaDecollo = 0;
	protected int attesaAtterraggio = 0;
	protected int aereiDecollati = 0;
	protected int aereiAtterrati = 0;
	protected int aereiParcheggiati = 0;
	protected int prenotaAtt = 0;
	protected String type_app="";
	
	//*****************DECOLLO********************
	public abstract void richAccessoPista(int io);
	public abstract void richAutorizDecollo(int io);
	public abstract void inVolo(int io);
	
	//****************ATTERRAGGIO******************
	public abstract void richAutorizAtterraggio(int io);
	public abstract void freniAttivati(int io);
	public abstract void inParcheggio(int io);

	public void title()
	{
		System.out.println("------------------------------------------------------------");
		System.out.println(TITLE);
		System.out.println(type_app);
		System.out.println("------------------------------------------------------------");
	}
	
	//*************MONITOR Aeroporto***************
	public void stampaSituazioneAeroporto()
	{
		System.out.println("Aerei in attesa in A per decollo:     "+attesaPista);
		System.out.println("Aerei in attesa in B per decollo:     "+attesaDecollo);
		System.out.println("Aerei in attesa per atterraggio       :     "+attesaAtterraggio);
		System.out.println("------------------------------------------------------------");
		System.out.println("Posti occupati in zona A:         "+(POSTI_A-liberiA)+"/"+POSTI_A);
		System.out.println("Posti occupati in zona B:         "+(POSTI_B-liberiB)+"/"+POSTI_B);
		System.out.println("------------------------------------------------------------");
		System.out.println("Aerei decollati    :     "+aereiDecollati);
		System.out.println("Aerei atterrati    :     "+aereiAtterrati);
		System.out.println("Aerei parcheggiati :     "+aereiParcheggiati);
		System.out.println("------------------------------------------------------------");	
	}
	
}