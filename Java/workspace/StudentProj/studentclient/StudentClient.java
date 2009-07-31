package studentclient;
import java.sql.SQLException;
import java.util.*;
import studentdao.StudentDAO;
import studentmodel.Student;

public class StudentClient {

	public static void main(String [] args) throws SQLException
	{
		StudentDAO dao = new StudentDAO();
		dao.initStudent();
		//------------创建一个学号为9821101的学生信息
		Student student = new Student();
		student.setAge(25);
		Calendar c = Calendar.getInstance();
		c.set(1980, Calendar.JANUARY, 1);
		student.setBirth(c.getTime());
		student.setId(1);
		student.setSex(Student.MALE);
		student.setUserId("9821101");
		student.setUserName("lizi");
		dao.addStudent(student);
		//-------------查询学号为9821101的学生信息
		Student s1 = dao.getStudent("9821101");
		//-------------修改学号为9821101的学生信息
		student.setUserName("shengyan");
		c.set(1980, Calendar.OCTOBER, 30);
		student.setBirth(c.getTime());
		dao.modifyStudent(student);
		//--------------删除学号为9821101的学生信息
		dao.deleteStudent("9821101");
		//--------------继续查询学号为9821101的学生信息
		dao.getStudent("9821101");
	}
}
