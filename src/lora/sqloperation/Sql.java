package lora.sqloperation;


import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import me.gacl.domain.sever;
import me.gacl.domain.profComparison;
import me.gacl.domain.LoRaAddress;
import me.gacl.domain.instruction;

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

	 @SuppressWarnings("finally")
	public String getDevProfIDforProfName(String name)
	 {
		 SqlSession session = sessionFactory.openSession(); 	 
		 String start="me.gacl.mapping.userMapper.profComparisonName";	
		 String ret="";
		 try
		 {
			 profComparison pr=session.selectOne(start, name);
			 if(pr!=null)
			 {
			    ret=pr.toString();
			 }
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
	 
	 @SuppressWarnings("finally")
	public String getIDPWDofLoRa()
	 {
		 SqlSession session = sessionFactory.openSession(); 	 
		 String start="me.gacl.mapping.userMapper.getLoRaAddress";	
		 String ret="";
		 try 
		 { 
			 List<LoRaAddress> lstUsers = session.selectList(start); 
			 String[] shu=lstUsers.toString().substring(1,lstUsers.toString().length()-1).split(",");
			 ret=shu[0]+","+shu[1];
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
	 
	 @SuppressWarnings("finally")
	public String getLoRaAddr()
	 {
		 SqlSession session = sessionFactory.openSession(); 	 
		 String start="me.gacl.mapping.userMapper.getLoRaAddress";	
		 String ret="";
		 try 
		 { 
			 List<LoRaAddress> lstUsers = session.selectList(start); 
			 String[] shu=lstUsers.toString().substring(1,lstUsers.toString().length()-1).split(",");
			 ret=shu[2];
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
	 
	 @SuppressWarnings("finally")
	public String setInsDeviceRX1(String insID,String insKey,String userID,String hwOpt,String devEui,String t)
	 {
		 SqlSession session = sessionFactory.openSession(); 	 
		 String start="me.gacl.mapping.userMapper.add_instruction";	
		 String ret="";
		 try 
		 {
			 instruction ins =new instruction();
			 ins.setInsID(insID);
			 ins.setUserID(userID);
			 ins.setOperationToken(insKey);
			 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
			 ins.setCreateTime(df.format(new Date()));
			 ins.setIsFin("0");
			 ins.setHwOpt(hwOpt);
			 ins.setReq(null);
			 ins.setDevEui(devEui);
			 ins.setT(t);
			 ins.setSt(null);
			 ins.setEt(null);
			 int retResult = session.update(start,ins);
			 session.commit();
			 if(retResult==1)
			 {
				 ret="1";
			 }
			 else
			 {
				 ret="0";
			 }
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
	 
	 @SuppressWarnings("finally")
	public String setInsDeviceRX2(String insID,String insKey,String userID,String hwOpt,String devEui,String st,String et)
	 {
		 SqlSession session = sessionFactory.openSession(); 	 
		 String start="me.gacl.mapping.userMapper.add_instruction";	
		 String ret="";
		 try 
		 {
			 instruction ins =new instruction();
			 ins.setInsID(insID);
			 ins.setUserID(userID);
			 ins.setOperationToken(insKey);
			 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss-SSS");
			 ins.setCreateTime(df.format(new Date()));
			 ins.setIsFin("0");
			 ins.setHwOpt(hwOpt);
			 ins.setReq(null);
			 ins.setDevEui(devEui);
			 ins.setT(null);
			 ins.setSt(st);
			 ins.setEt(et);
			 int retResult = session.update(start,ins);
			 session.commit();
			 if(retResult==1)
			 {
				 ret="1";
			 }
			 else
			 {
				 ret="0";
			 }
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
	 
	 @SuppressWarnings("finally")
	public String hasSameInsPara(String insID,String operationToken)
	 {
		 SqlSession session = sessionFactory.openSession(); 	 
	     String start="me.gacl.mapping.userMapper.select_insID_and_operationToken";	
		 String ret="";
		 try
		 {
			 instruction ins =new instruction();
			 ins.setInsID(insID);
			 ins.setOperationToken(operationToken);
			 List<instruction> shuchu=session.selectList(start, ins);
			 if(shuchu.toString()!="[]")
			 {
				 ret=shuchu.toString().substring(1,shuchu.toString().length()-1);
			 }
			 else
			 {			 
				 ret= "0";
			 }
		 } 
		 catch(Exception ex)
		 {
			 ret= "e:"+ex.toString();
			 ex.printStackTrace();
		 }
		 finally
		 {
			 session.close();
			 return ret;
		 }
	 }
}
