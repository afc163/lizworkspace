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
	
	//数据库用户名
	String userName = "root";
    //数据库密码
    String userPassword = "lizzie1985";
    //所要链接的数据库地址
    String url = "jdbc:mysql://localhost:3306/db";
    //数据库连接对象
    Connection dbConn;
    //存贮错误信息的变量
    String err;

    public JDBCCon() {
    	err = "";
    	dbConn = null;
    }
    
    //连接数据库，返回一个Connection类型对象
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