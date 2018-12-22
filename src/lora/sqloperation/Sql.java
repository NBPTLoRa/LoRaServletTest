package lora.sqloperation;


import java.io.InputStream;
import java.util.List;

import me.gacl.domain.sever;
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
		
	 public String getDisServIP()
	 {
			 
		 String start="me.gacl.mapping.userMapper.DistServIP";	
		 String ret;
		 try 
		 { 
			 List<sever> lstUsers = session.selectList(start); 
			 ret=lstUsers.toString().substring(1,lstUsers.toString().length()-1);
			 session.close();
			 return ret;
		 }	
		 catch(Exception ex)
		 {
			 ret="e"+ex.toString();
			 session.close();
			 return ret;
		 }
		 
	 }
}
