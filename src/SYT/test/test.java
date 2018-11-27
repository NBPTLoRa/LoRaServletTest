package SYT.test;

import java.io.IOException;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import SYT.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class test {

	public static void main(String[] args) throws IOException {
        String resource = "conf.xml";
     //   InputStream is = test.class.getClassLoader().getResourceAsStream(resource);
       // SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        Reader reader = Resources.getResourceAsReader(resource); 
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);

	    SqlSession session = sessionFactory.openSession(); 
	    String start="SYT.mapping.userMapper.getAllUser";
        List<User> use=session.selectList(start);
        session.close();
        System.out.println(use);
	}

}
