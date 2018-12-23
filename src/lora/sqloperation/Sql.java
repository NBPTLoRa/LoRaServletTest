package lora.sqloperation;


import java.io.InputStream;
import java.util.List;

import me.gacl.domain.sever;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Sql {
	SqlSessionFactory sessionFactory;
	public Sql(){
		String resource = "conf.xml";	      
		InputStream is = Sql.class.getClassLoader().getResourceAsStream(resource);	        
		sessionFactory = new SqlSessionFactoryBuilder().build(is);
	}
		
	 @SuppressWarnings("finally")
	public String getServerIP()
	 {
		 SqlSession session = sessionFactory.openSession(); 	 
		 String start="me.gacl.mapping.userMapper.DistServIP";	
		 String ret="";
		 try 
		 { 
			 List<sever> lstUsers = session.selectList(start); 
			 ret=lstUsers.toString().substring(1,lstUsers.toString().length()-1);
		 }	
		 catch(Exception ex)
		 {
			 ret="e:"+ex.toString();
			 ex.printStackTrace();
		 }
		 finally
		 {
			 session.close();
			 return ret;
		 }
		 
	 }
}
