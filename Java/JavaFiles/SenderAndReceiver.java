public class SenderAndReceiver
{
	public static void main(String [] args)
	{
		Buffer bf = new Buffer();
		(new Sender(bf)).start();
		(new Receiver(bf)).start();
	}
}

class Sender extends Thread
{
	private Buffer bf;
	public Sender(Buffer bf)
	{
		this.bf = bf;
	}
	
	public void run()
	{
		for (int i = 1; i < 6; i++)
		{
			bf.put(i);
			System.out.println("Sender put: " + i);
		}
	}
}

class Receiver extends Thread
{
	private Buffer bf;
	public Receiver(Buffer bf)
	{
		this.bf = bf;
	}
	
	public void run()
	{
		for (int i = 1; i < 6; i++)
		{
			System.out.println("Receiver get: " + bf.get());
		}
	}
}

class Buffer
{
	private int value;
	private boolean isEmpty = true;
	
	synchronized void put(int i)
	{
		while (!isEmpty)
		{
			try
			{
				this.wait();
			}
			catch (InterruptedException e)
			{
				System.out.println(e.getMessage());
			}
		}
		value = i;
		isEmpty = false;
		notify();
	}
	
	synchronized int get()
	{
		while (isEmpty)
		{
			try
			{
				this.wait();
			}
			catch (InterruptedException e)
			{
				System.out.println(e.getMessage());
			}
		}
		isEmpty = true;
		notify();
		return value;
	}
}