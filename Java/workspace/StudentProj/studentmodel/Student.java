package studentmodel;
import java.util.Date;

public class Student {

	public static final int MALE = 0;
	public static final int FEMALE = 1;
	private long id;//学生编号
	private String userId;//学生学号
	private String userName;//学生性别
	private int sex;//学生年龄
	private int age;
	private Date birth;
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public long getId()
	{
		return id;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getUserId()
	{
		return userId;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setSex(int sex)
	{
		this.sex = sex;
	}
	
	public int getSex()
	{
		return sex;
	}
	
	public void setAge(int age)
	{
		this.age = age;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setBirth(Date birth)
	{
		this.birth = birth;
	}
	
	public Date getBirth()
	{
		return birth;
	}
}
