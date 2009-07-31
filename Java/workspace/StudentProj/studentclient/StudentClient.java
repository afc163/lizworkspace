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
		//------------����һ��ѧ��Ϊ9821101��ѧ����Ϣ
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
		//-------------��ѯѧ��Ϊ9821101��ѧ����Ϣ
		Student s1 = dao.getStudent("9821101");
		//-------------�޸�ѧ��Ϊ9821101��ѧ����Ϣ
		student.setUserName("shengyan");
		c.set(1980, Calendar.OCTOBER, 30);
		student.setBirth(c.getTime());
		dao.modifyStudent(student);
		//--------------ɾ��ѧ��Ϊ9821101��ѧ����Ϣ
		dao.deleteStudent("9821101");
		//--------------������ѯѧ��Ϊ9821101��ѧ����Ϣ
		dao.getStudent("9821101");
	}
}
