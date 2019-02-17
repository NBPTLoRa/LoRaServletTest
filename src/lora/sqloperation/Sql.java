package lora.sqloperation;


import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.gacl.domain.sever;
import me.gacl.domain.profComparison;
import me.gacl.domain.LoRaAddress;
import me.gacl.domain.instruction;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import com.google.gson.JsonObject;

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
	public String setInsDeviceRX2(String insID,String insKey,String userID,String hwOpt,String devEui,String et)
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
			 ins.setSt(null);
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
	 
	 public String newtime(String t)
	 {
		 int i=Integer.parseInt(t)*60;
		 Calendar now=Calendar.getInstance();  
		 now.add(Calendar.MINUTE,-(i));   
		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss.SSSSSS"); 
		 String dateStr=sdf.format(now.getTimeInMillis());
		 return dateStr;
	 }
	 
	 public ArrayList<String> DB(String devEui,String t)
	 {
		 ArrayList<String> ret=new ArrayList<String>();
		 String[] shuzhu=new String[2];
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss.SSSSSS");
		 String[] time_1=newtime(t).split(",");
		 String[] time_2=df.format(new Date()).split(",");
		 shuzhu[0]=time_1[0]+"T"+time_1[1]+"Z";
		 shuzhu[1]=time_2[0]+"T"+time_2[1]+"Z";
		 InfluxDB iDB=InfluxDBFactory.connect("http://47.101.172.221:8086", "admin", "admin");		
		 String table="device_frmpayload_data_temperature";
		 String sqlcom="SELECT time,dev_eui,device_name,value FROM device_frmpayload_data_temperature where dev_eui = '"+devEui+"'and time>='"+shuzhu[0]+"' and time <='"+shuzhu[1]+"'";
		 String database="LoRaDB";
			Query query=new Query(sqlcom, database);
			try {
			QueryResult qs=iDB.query(query);

			for(Result temp:qs.getResults())
			{
				List<Series> series = temp.getSeries();
				for(Series serie : series){
					List<List<Object>> values = serie.getValues();
					for(List<Object> n : values){
						ret.add(n.toString());
					}
				}
			}
			}
			catch(Exception ex)
			{
				ret.add("0");
			}
		 return ret;
	 }
	 
	 @SuppressWarnings("finally")
	public ArrayList<String> getDeviceRX1(String insID,String operationToken)
	 {
		 SqlSession session = sessionFactory.openSession(); 	 
	     String start="me.gacl.mapping.userMapper.select_devEui_and_t";	
	     ArrayList<String> ret=new ArrayList<String>();
		 try
		 {
			 instruction ins =new instruction();
			 ins.setInsID(insID);
			 ins.setOperationToken(operationToken);
			 List<instruction> shuchu=session.selectList(start, ins);
			 if(shuchu.toString()!="[]")
			 {
				 String[] shu=shuchu.toString().substring(1,shuchu.toString().length()-1).split(",");
				 ret=DB(shu[0],shu[1]);
			 }
			 else
			 {			 
				 ret.add("0");
			 }
		 } 
		 catch(Exception ex)
		 {
			 ret.add("e:"+ex.toString());
			 ex.printStackTrace();
		 }
		 finally
		 {
			 session.close();
			 return ret;
		 }
	 }
}
