import javax.swing.*;

public class InputTest
{
	public static void main(String args[])
	{
		String name = JOptionPane.showInputDialog("please input your name!");
		String input = JOptionPane.showInputDialog("please input your age!");
		int age = Integer.parseInt(input);
		System.out.println("Hello, " + name + "! Your age is: " + input);
		System.exit(0);
	}
}