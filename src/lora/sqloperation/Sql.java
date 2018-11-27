package lora.sqloperation;

import java.io.IOException;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import SYT.domain.User;
import org.apache.ibatis.io.Resources;
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
	
}
