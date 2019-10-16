package ProducerConsumer;

public abstract class ProducerConsumer 
{
	public static int DIM_BUFFER = 10;
	protected int num_read_done=0;
	protected int num_write_done=0;
	protected int num_free=DIM_BUFFER;
	protected int num_occupied=0;
	
	public abstract int read(int num_read);
	public abstract int write(int num_write);
	
	public void state()
	{
		System.out.println("--------------------------------------------------------");
		System.out.println("  Richieste letture completate: "+num_read_done);
		System.out.println("  Richieste scritture completate: "+num_write_done);
		System.out.println("--------------------------------------------------------");
		System.out.println("  liberi_Buffer: "+num_free+"  occupati_Buffer:"+num_occupied);
		System.out.println("--------------------------------------------------------");
	}
	
}
