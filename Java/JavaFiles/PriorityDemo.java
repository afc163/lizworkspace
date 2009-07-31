public class PriorityDemo implements Runnable
{
	public static void main(String [] args)
	{
		PriorityDemo run = new PriorityDemo();
		Thread thread;
		
		//3�������ȼ��߳�
		for (int i = 0; i < 3; i++)
		{
			thread = new Thread(run);
			thread.setPriority(5);
			thread.start();
		}
		
		//���ø����ȼ��߳�ִ��ʱ�䳤���߳�
		LongerTime longer = new LongerTime();
		Thread longerThread = new Thread(longer);
		longerThread.setPriority(6);
		longerThread.start();
		
		//���ø����ȼ��߳�
		for (int i = 0; i < 3; i++)
		{
			thread = new Thread(run);
			thread.setPriority(4);
			thread.start();
		}
	}
	
	public void run()
	{
		long startTimer = System.currentTimeMillis();
		
		float sum = 0;
		for (int i = 0; i <= 100000; i++)
		{
			for (int j = 0; j <= 1000; j++)
			{
				sum = sum + 1;
			}
		}
		
		long endTimer = System.currentTimeMillis();
		System.out.println(Thread.currentThread().getName() + " execution time: " + (endTimer - startTimer) / 1000.0);
	}
}

class LongerTime implements Runnable
{
	public void run()
	{
		long startTimer = System.currentTimeMillis();
		
		float sum = 0;
//		for (int i = 0; i <= 100000; i++)
//		{
//			for (int j = 0; j <= 2000; j++)
//			{
//				sum = sum + 1;
//			}
//		}
		for (int i = 0; i <= 100000; i++)
		{
			for (int j = 0; j <= 2000; j++)
			{
				sum = sum + 1;
			}
			
			try
			{
				Thread.sleep(1);
			}
			catch (Exception e)
			{
			}
		}		
		long endTimer = System.currentTimeMillis();
		System.out.println("Longer time thread execution time: " + (endTimer - startTimer) / 1000.0);
	}
}