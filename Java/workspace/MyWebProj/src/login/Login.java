package login;

public class Login
{
    //�������Ա����
    public String userName;
    public String password;
    
    //��ʼ�����Ա����
    public Login()
    {
        this.userName = null;
        this.password = null;
    }
    
    //������Ա����userName��ֵ
    public void setUserName(String s)
    {
        this.userName = s;
    }
    
    //�������Ա����userName��ֵ
    public String getUserName()
    {
        return this.userName;
    }
    
    //������Ա����password��ֵ
    public void setPassword(String s)
    {
        this.password = s;
    }
    
    //�������Ա����userName��ֵ
    public String getPassword()
    {
        return this.password;
    }
}