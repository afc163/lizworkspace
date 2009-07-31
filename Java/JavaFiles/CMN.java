import javax.swing.JOptionPane;

public class CMN
{
	public static void main(String [] args)
	{
		int totalBall;
		
		try
		{	
			//totalBall = Integer.parseInt(JOptionPane.showInputDialog("请输入数目"));
		}
		catch (Exception e)
		{
			System.out.println("输入错误 " + e.getMessage());
			return;
		}
		totalBall = 10;
		Permutation1 permutation = new Permutation1(totalBall);
		long n = 0;
		//for (int i = 1; i < totalBall; i++)
		//{
			n = permutation.perm(4);
		//}
		System.out.println(n);
	}
}

class Permutation1
{
	private int totalBall;
	
	public Permutation1(int tBall)
	{
		totalBall = tBall;
	}
	
	public long perm(int i)
	{
		long n = 0;
		int [] first = new int[i];
		int [] end = new int[i];
		for (int ii = 0; ii < i; ii++){
			first[ii] = ii+1;
			end[i-1-ii] = totalBall - ii;
		}
		int change = i-1;
		while (true){
			for (int j=0; j < i; j++){
				System.out.print(first[j]);
				System.out.print(',');
			}
			System.out.println();
			n += 1;
			
			if (first[change] != end[change]){
				first[change]++;
			}
			else{
				change--;
				if (change<0){
					break;
				}
				first[change]++;
				int newchange = change;
				for (int k=change+1; k<i; k++){
					first[k] = first[k-1]+1;
					if (first[k] != end[k]){
						newchange = k;
					}
				}
				change = newchange;
			}
		}
		return n;
	}
}
