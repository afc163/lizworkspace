public class RunnableDemo implements Runnable
{
	private String threadName;
	private long delay;
	
	public static void main(String [] args)
	{
		RunnableDemo run1 = new RunnableDemo("Thread 1", 200);
		RunnableDemo run2 = new RunnableDemo("Thread 2", 400);
		RunnableDemo run3 = new RunnableDemo("Thread 3", 800);
		
		Thread thread1 = new Thread(run1);
		Thread thread2 = new Thread(run2);
		Thread thread3 = new Thread(run3);
		
		thread1.setDaemon(true);
		thread2.setDaemon(true);
		thread3.setDaemon(true);
		
		thread1.start();
		thread2.start();
		thread3.start();
		
		System.out.println("Press \"Enter\" to stop ...");
		try
		{
			System.in.read();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		System.out.println("End of main()");
	}
	
	public RunnableDemo(String threadName, long delay)
	{
		this.threadName = threadName;
		this.delay = delay;
	}
	
	public void run()
	{
		try
		{
			while (true)
			{
				System.out.println(threadName);
				Thread.sleep(delay);
			}
		}
		catch (InterruptedException e)
		{
			System.out.println(threadName + " got interrupted");
		}
	}
}