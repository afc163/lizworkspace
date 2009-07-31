public class InterruptedDemo extends Thread
{
	private String threadName;
	private long delay;
	
	public static void main(String [] args)
	{
		InterruptedDemo thread1 = new InterruptedDemo("Thread 1", 200);
		InterruptedDemo thread2 = new InterruptedDemo("Thread 2", 400);
		InterruptedDemo thread3 = new InterruptedDemo("Thread 3", 800);
		
		thread1.start();
		thread2.start();
		thread3.start();
		
		System.out.println("Press \"Enter\" to stop ...");
		try
		{
			System.in.read();
			thread1.interrupt();
			thread2.interrupt();
			thread3.interrupt();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		System.out.println("End of main()");
	}
	
	public InterruptedDemo(String threadName, long delay)
	{
		this.threadName = threadName;
		this.delay = delay;
		//setDaemon(true);
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