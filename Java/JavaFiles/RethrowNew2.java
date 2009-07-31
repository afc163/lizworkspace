public class RethrowNew2
{
	public static void f() throws Exception
	{
		System.out.println("originating the exception in f()");
		throw new Exception("throw from f()");
	}
	
	public static void g() throws NullPointerException
	{
		try
		{
			f();
		}
		catch(Exception e)
		{
			System.out.println("Caught in g(), e.printStackTrace():");
			e.printStackTrace();
			
			throw new NullPointerException("from main");
		}
	}
	
	public static void main(String [] args)
	{
		try
		{
			g();
		}
		catch(NullPointerException e)
		{
			System.out.println("Caught in main, e.printStackTrace():");
			e.printStackTrace();
		}
	}
}