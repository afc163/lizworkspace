package login;

public class Login
{
    //定义类成员变量
    public String userName;
    public String password;
    
    //初始化类成员变量
    public Login()
    {
        this.userName = null;
        this.password = null;
    }
    
    //设置类员变量userName的值
    public void setUserName(String s)
    {
        this.userName = s;
    }
    
    //返回类成员变量userName的值
    public String getUserName()
    {
        return this.userName;
    }
    
    //设置类员变量password的值
    public void setPassword(String s)
    {
        this.password = s;
    }
    
    //返回类成员变量userName的值
    public String getPassword()
    {
        return this.password;
    }
}