import java.awt.event.*;
import java.text.*;
import javax.swing.*;
public class InnerClass
{
	public static void main(String [] args)
	{
		TimerCount count = new TimerCount(1000);
		count.start(10);
		
		JOptionPane.showMessageDialog(null, "Quir Program?");
		System.exit(0);
	}
}

class TimerCount
{
	public TimerCount(double initialBalance)
	{
		balance = initialBalance;
	}
	
	public void start(double rate)
	{
		ActionListener adder = new InterestAdd(rate);
		Timer t = new Timer(1000, adder);
		t.start();
	}
	
	private class InterestAdd implements ActionListener
	{
		public InterestAdd(double aRate)
		{
			rate = aRate;
		}
		
		public void actionPerformed(ActionEvent event)
		{
			double interest = balance * rate / 100;
			balance += interest;
			
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			System.out.println("balance=" + formatter.format(balance));
		}
	}
	
	private double balance;
	private double rate;
}