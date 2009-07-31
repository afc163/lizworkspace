public class StringDemo
{
	public static void main(String [] args)
	{
		String str = "this is my original string";
		String toDelete = " original";
		//if (str.startWith(toDelete))
		//{
		int index = str.indexOf(toDelete);
		if (index != -1)
		{
			//String str1 = str.substring(0, index);
			//String str2 = str.substring(index + toDelete.length());
			StringBuffer bufStr = new StringBuffer(str);
			bufStr.delete(index, index + toDelete.length());
			str = bufStr.toString();
			//str = str1 + str2;
			System.out.println(bufStr);
		}
		else
		{
			System.out.println("string \"" + toDelete + "\" not found");
		}
	}
}
			