package studentdao;

import java.sql.*;
import java.text.SimpleDateFormat;
import studentmodel.Student;

public class StudentDAO {

	private String jdbcDriver;//JDBC���ݿ�����
	private String jdbcURL;//JDBC�����ݿ�����
	private String dbUserName;//���ݿ��û���
	private String dbPassword;//���ݿ�����
	SimpleDateFormat df = new SimpleDateFormat("yyyy �� mm �� dd ��");
	
	/**Ĭ�ϵĹ��캯��
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
	
	/**��ȡ���ݿ������
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
	
	/**�ر����ݿ�ResultSet��PreparedStatement��Connection
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
	
	/**�ر����ݿ�PreparedStatement��Connection
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
	
	/**���ݿ�����ã��������ѧ����Ϣ
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
			System.out.println("--------------���ݿ��ʼ�ɹ�-----------------");
		}
		finally
		{
			closeStatement(conn, stmt);
		}
	}
	
	/**����һ��ѧ��
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
			System.out.println("---------����ѧ���ɹ�-----------");
			System.out.println("ѧ��ѧ�ţ�" + student.getUserId());
			System.out.println("ѧ��������" + student.getUserName());			
		}
		catch (SQLException e)
		{
			System.out.println("---------����ѧ��ʧ��------------");
			System.out.println("ԭ�����£�" + e.getMessage());
		}
		finally
		{
			closeStatement(conn, stmt);
		}
	}
	
	/**����ѧ�Ż�ȡѧ����Ϣ
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
				
				System.out.println("-------------��ѯѧ���ɹ�------------");
				System.out.println("ѧ��ѧ�ţ�" + student.getUserId());
				System.out.println("ѧ��������" + student.getUserName());
			}
			else
			{
				System.out.println("--------------��ѯѧ��ʧ��----------------");
				System.out.println("�û���Ϣ�����ڣ�����ѧ���Ƿ���д��ȷ");
			}
		}
		catch (SQLException e)
		{
			System.out.println("------------��ѯѧ��ʧ��-----------------");
			System.out.println("ԭ�����£�" + e.getMessage());
		}
		finally
		{
			closeResultSet(conn, stmt, rs);
		}
		return student;
	}
	
	/**�޸�ѧ����Ϣ
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
			System.out.println("--------------�޸�ѧ���ɹ�--------------");
			System.out.println("ԭѧ��ѧ�ţ�" + oldStudent.getUserId());
			System.out.println("ԭѧ��������" + oldStudent.getUserName());
			System.out.println("ԭѧ�������գ�" + df.format(oldStudent.getBirth()));
			System.out.println("��ѧ��ѧ�ţ�" + student.getUserId());
			System.out.println("��ѧ��������" + student.getUserName());
			System.out.println("��ѧ�������գ�" + df.format(student.getBirth()));
		}
		catch (SQLException e)
		{
			System.out.println("------------�޸�ѧ��ʧ��-------------");
			System.out.println("ԭ�����£�" + e.getMessage());
		}
		finally
		{
			closeStatement(conn, stmt);
		}
	}
	
	/**ɾ��ѧ����Ϣ
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
			System.out.println("-----------ɾ��ѧ���ɹ�----------");
			System.out.println("ѧ��ѧ�ţ�" + student.getUserId());
			System.out.println("ѧ��������" + student.getUserName());
		}
		catch (SQLException e)
		{
			System.out.println("-------------ɾ��ѧ��ʧ��-----------");
			System.out.println("ԭ�����£�" + e.getMessage());
		}
		finally
		{
			closeStatement(conn, stmt);
		}		
	}
}
