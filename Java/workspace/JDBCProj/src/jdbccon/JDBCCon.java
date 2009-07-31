/**
 * @(#)JDBCCon.java
 *
 *
 * @shengyan 
 * @version 1.00 2007/7/7
 */

package jdbccon;

import java.sql.*;

public class JDBCCon {
	
	//���ݿ��û���
	String userName = "root";
    //���ݿ�����
    String userPassword = "lizzie1985";
    //��Ҫ���ӵ����ݿ��ַ
    String url = "jdbc:mysql://localhost:3306/db";
    //���ݿ����Ӷ���
    Connection dbConn;
    //����������Ϣ�ı���
    String err;

    public JDBCCon() {
    	err = "";
    	dbConn = null;
    }
    
    //�������ݿ⣬����һ��Connection���Ͷ���
    public Connection getConn()
    {
    	try
    	{
    		Class.forName("org.gjt.mm.mysql.Driver");
    		dbConn = DriverManager.getConnection(url, userName, userPassword);
    	}
    	catch (Exception ex)
    	{
    		dbConn = null;
    		err = ex.toString();
    		System.out.println(err);
    	}
    	return dbConn;
    }
    
    public String getErr()
    {
    	return err;
    }    
}