public class SynchronizedBlockDemo implements Runnable
{
	public static void main(String [] args)
	{
		SynchronizedBlockDemo run = new SynchronizedBlockDemo();
		Thread thread;
		for (int i = 0; i < 3; i++)
		{
			thread = new Thread(run);
			thread.start();
		}
	}
	
	public void run()
	{
		for (int i = 0; i < 3; i++)
		{
			Synchronized(System.out)
			{
				System.out.println(Thread.currentThread().getName());
				System.out.println("Count: " + i);
			}
		}
	}
}