import java.util.Timer;
import java.util.TimerTask;

public class myTimer 
{
	private final Timer timer = new Timer();
	private final int minutes;

 	public myTimer(int minutes) 
	{
 		this.minutes = minutes;
 	}

 	public void start() 
	{
 		timer.schedule(new TimerTask() 
			{
 				public void run() 
				{
 					System.out.println("sdfkjl");					
  				}
 			}, minutes * 500, 500);
//timer.cancel();
 	}

	public static void main(String[] args) 
	{
		myTimer mTimer = new myTimer(1);
 		mTimer.start();
	}
}