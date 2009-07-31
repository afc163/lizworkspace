public class SimpleThreadDemo extends Thread
{
	private String threadName;
	private long delay;
	
	public static void main(String [] args)
	{
		SimpleThreadDemo thread1 = new SimpleThreadDemo("Thread 1", 200);
		SimpleThreadDemo thread2 = new SimpleThreadDemo("Thread 2", 400);
		SimpleThreadDemo thread3 = new SimpleThreadDemo("Thread 3", 800);
		
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
	
	public SimpleThreadDemo(String threadName, long delay)
	{
		this.threadName = threadName;
		this.delay = delay;
		setDaemon(true);
	}
	
	public void run()
	{
		try
		{
			while (true)
			{
				System.out.println(threadName);
				sleep(delay);
			}
		}
		catch (InterruptedException e)
		{
			System.out.println(threadName + " got interrupted");
		}
	}
}