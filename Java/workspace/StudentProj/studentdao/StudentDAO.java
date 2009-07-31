package studentdao;

import java.sql.*;
import java.text.SimpleDateFormat;
import studentmodel.Student;

public class StudentDAO {

	private String jdbcDriver;//JDBC数据库驱动
	private String jdbcURL;//JDBC的数据库连接
	private String dbUserName;//数据库用户名
	private String dbPassword;//数据库密码
	SimpleDateFormat df = new SimpleDateFormat("yyyy 年 mm 月 dd 日");
	
	/**默认的构造函数
	 */
	public StudentDAO()
	{
		jdbcDriver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		jdbcURL = "jdbc:microsoft:sqlserver://127.0.0.1:1433;DatabaseName=StudentSQL";
		dbUserName = "sa";
		dbPassword = "lizzie1985";
	}
	
	public StudentDAO(String jdbcDriver, String jdbcURL, String dbUserName, String dbPassword)
	{
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL = jdbcURL;
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
	}
	
	/**获取数据库的连接
	 */
	private Connection getConnection() throws SQLException
	{
		try
		{
			Class.forName(jdbcDriver);
		}
		catch (ClassNotFoundException e)
		{
			throw new SQLException(e.getMessage());
		}
		return DriverManager.getConnection("jdbc:sqlserver://localhost;user=shengyan;password=lizzie1985");//jdbcURL, dbUserName, dbPassword);
	}
	
	/**关闭数据库ResultSet、PreparedStatement和Connection
	 */
	private void closeResultSet(Connection conn, PreparedStatement stmt, ResultSet rs)
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
		}
		catch (Exception e)
		{
		}
		
		try
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}
		catch (Exception e)
		{
		}
		
		try
		{
			if (!conn.isClosed())
			{
				conn.close();
			}
		}
		catch (Exception e)
		{
		}
	}
	
	/**关闭数据库PreparedStatement和Connection
	 */
	private void closeStatement(Connection conn, PreparedStatement stmt)
	{
		try
		{
			if (stmt != null)
			{
				stmt.close();
			}
		}
		catch (Exception e)
		{
		}
		
		try
		{
			if (!conn.isClosed())
			{
				conn.close();
			}
		}
		catch (Exception e)
		{
		}
	}
	
	/**数据库表重置，清空所有学生信息
	 */
	public void initStudent() throws SQLException
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = getConnection();
			String strSQL = "delete from students";
			stmt = conn.prepareStatement(strSQL);
			stmt.executeUpdate();
			System.out.println("--------------数据库初始成功-----------------");
		}
		finally
		{
			closeStatement(conn, stmt);
		}
	}
	
	/**创建一个学生
	 */
	public void addStudent(Student student)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = getConnection();
			String strSQL = "insert into students" + 
			                "(id, user_id, user_name, sex, age, birth)" +
			                "values(?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(strSQL);
			stmt.setLong(1, student.getId());
			stmt.setString(2, student.getUserId());
			stmt.setString(3, student.getUserName());
			stmt.setInt(4, student.getSex());
			stmt.setInt(5, student.getAge());
			stmt.setDate(6, new java.sql.Date(student.getBirth().getTime()));
			stmt.executeUpdate();
			System.out.println("---------创建学生成功-----------");
			System.out.println("学生学号：" + student.getUserId());
			System.out.println("学生姓名：" + student.getUserName());			
		}
		catch (SQLException e)
		{
			System.out.println("---------创建学生失败------------");
			System.out.println("原因如下：" + e.getMessage());
		}
		finally
		{
			closeStatement(conn, stmt);
		}
	}
	
	/**根据学号获取学生信息
	 */
	public Student getStudent(String userId)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Student student = null;
		try
		{
			conn = getConnection();
			String strSQL = "select * fron studentd where user_id = ?";
			stmt = conn.prepareStatement(strSQL);
			stmt.setString(1, userId);
			rs = stmt.executeQuery();
			if (rs.next())
			{
				student = new Student();
				student.setId(rs.getLong("id"));
				student.setBirth(rs.getDate("birth"));
				student.setAge(rs.getInt("age"));
				student.setSex(rs.getInt("sex"));
				student.setUserId(rs.getString("user_id"));
				student.setUserName(rs.getString("user_name"));
				
				System.out.println("-------------查询学生成功------------");
				System.out.println("学生学号：" + student.getUserId());
				System.out.println("学生姓名：" + student.getUserName());
			}
			else
			{
				System.out.println("--------------查询学生失败----------------");
				System.out.println("用户信息不存在，请检查学号是否填写正确");
			}
		}
		catch (SQLException e)
		{
			System.out.println("------------查询学生失败-----------------");
			System.out.println("原因如下：" + e.getMessage());
		}
		finally
		{
			closeResultSet(conn, stmt, rs);
		}
		return student;
	}
	
	/**修改学生信息
	 */
	public void modifyStudent(Student student)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		Student oldStudent = getStudent(student.getUserId());
		try
		{
			conn = getConnection();
			String strSQL = "update students set user_id = ?, user_name = ?, sex = ?, age = ?, birth = ? where id = ?";
			stmt = conn.prepareStatement(strSQL);
			stmt.setString(1, student.getUserId());
			stmt.setString(2, student.getUserName());
			stmt.setInt(3, student.getSex());
			stmt.setInt(4, student.getAge());
			stmt.setDate(5, new java.sql.Date(student.getBirth().getTime()));
			stmt.setLong(6, student.getId());
			stmt.executeUpdate();
			System.out.println("--------------修改学生成功--------------");
			System.out.println("原学生学号：" + oldStudent.getUserId());
			System.out.println("原学生姓名：" + oldStudent.getUserName());
			System.out.println("原学生出生日：" + df.format(oldStudent.getBirth()));
			System.out.println("现学生学号：" + student.getUserId());
			System.out.println("现学生姓名：" + student.getUserName());
			System.out.println("现学生出生日：" + df.format(student.getBirth()));
		}
		catch (SQLException e)
		{
			System.out.println("------------修改学生失败-------------");
			System.out.println("原因如下：" + e.getMessage());
		}
		finally
		{
			closeStatement(conn, stmt);
		}
	}
	
	/**删除学生信息
	 */
	public void deleteStudent(String userId)
	{
		Connection conn = null;
		PreparedStatement stmt =null;
		Student student = getStudent(userId);
		try
		{
			conn = getConnection();
			String strSQL = "delete from students where user_id = ?";
			stmt = conn.prepareStatement(strSQL);
			stmt.setString(1, userId);
			stmt.executeUpdate();
			System.out.println("-----------删除学生成功----------");
			System.out.println("学生学号：" + student.getUserId());
			System.out.println("学生姓名：" + student.getUserName());
		}
		catch (SQLException e)
		{
			System.out.println("-------------删除学生失败-----------");
			System.out.println("原因如下：" + e.getMessage());
		}
		finally
		{
			closeStatement(conn, stmt);
		}		
	}
}
