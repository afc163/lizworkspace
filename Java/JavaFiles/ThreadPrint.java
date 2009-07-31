public class ThreadPrint extends Thread
{
	int k = 0;
	public ThreadPrint(String name, int k)
	{
		super(name);
		this.k = k;
	}
	
	public void run()
	{
		int i = k;
		System.out.println();
		System.out.print(getName() + ": ");
		
		while (i < 50)
		{
			System.out.print(i + " ");
			i += 2;
		}
		
		System.out.println(getName() + " end!");
	}
	
	public static void main(String[] args)
	{
		ThreadPrint t1 = new ThreadPrint("Thread1", 1);
		ThreadPrint t2 = new ThreadPrint("Thread2", 2);
		
		t1.start();
		t2.start();
		
		System.out.println("activeCount=" + t2.activeCount());
	}
}