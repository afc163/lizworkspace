package handledata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class connectDB {

	/**
	 * @param args
	 */
	public Connection conn;
	public connectDB()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/galicia", "root", "201660");
			//Statement stmt = conn.createStatement();
			//ResultSet rs = stmt.executeQuery("select * from info");
			///rs.next();
			
			//System.out.print(rs.getString(2));
			// Do something with the Connection
			//rs.close();
			//stmt.close();
			//conn.close();

		} catch (Exception e) {
			System.out.println("出错了");
			System.out.println(e);
		}
	}
	
	public void createTable(String sql_del,String sql)
	{
		try
		{
			Statement s=conn.createStatement();
			s.executeUpdate(sql_del);
			s.executeUpdate(sql);
			s.close();
		}catch(SQLException e)
		{
			//用于防止删除不存在得表
			if(e.getMessage().equals("Unknown table 'cjj'"))
			{
				try
				{
					Statement s=conn.createStatement();
					s.executeUpdate(sql);
					s.close();
				}catch(SQLException ee)
				{
					System.out.println("create table error!");
				}
			}
			System.out.println("create table error!");
		}
	}
	
	public void executeSql(String sql)
	{
		try
		{
			Statement s=conn.createStatement();
			s.executeUpdate(sql);
			s.close();
		}catch(SQLException e)
		{
			System.out.println("execute sql error!");
		}
	}

}
