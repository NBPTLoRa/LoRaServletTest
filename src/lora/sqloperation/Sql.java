package lora.sqloperation;


import java.io.InputStream;
import java.util.List;

import SYT.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Sql {
	SqlSession session;
	public Sql(){
		String resource = "conf.xml";	      
		InputStream is = Sql.class.getClassLoader().getResourceAsStream(resource);	        
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
		session = sessionFactory.openSession(); 
	}
	
	public List<User> ALL()
	{
	    String start="SYT.mapping.userMapper.getAllUser";
	    List<User> use=null;
	    try
	    {
	    	use=session.selectList(start);
	    }
	    catch(Exception ex)
	    {
	    	use=null;
	    }
	    finally
	    {
	    	session.close();
	    	return use;
	    }
	}
	
	public String Add(int id,String name,int age)
	{
	    String start="SYT.mapping.userMapper.addUser";
	    User use =new User();
	    try
	    {
	    	use.setId(id);
	    	use.setName(name);
	    	use.setAge(age);
	    	session.insert(start, use);
	        session.commit();
	        session.close();
			return "成功";
	    }
	    catch(Exception ex)
	    {
	    	session.close();
	    	return "错误";
	    }
	}
	
	public String Delete(int id)
	{
	    String start="SYT.mapping.userMapper.delete";
	    User use =new User();
	    try
	    {
	    	session.delete(start, id);
	        session.commit();
	        session.close();
			return "成功";
	    }
	    catch(Exception ex)
	    {
	    	session.close();
	    	return "错误";
	    }
	}
}
