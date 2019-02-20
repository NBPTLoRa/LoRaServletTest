package lora.sqloperation;


import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParsePosition;
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
	public String setInsDeviceRX2(String insID,String insKey,String userID,String hwOpt,String devEui,String t,String et)
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
	     String start="me.gacl.mapping.userMapper.select_hwOpt_and_isFin";	
		 String ret="";
		 try
		 {
			 instruction ins =new instruction();
			 ins.setInsID(insID);
			 ins.setOperationToken(operationToken);
			 List<instruction> shuchu=session.selectList(start, ins);
			 if(shuchu.toString()!="[]")
			 {
				 String []shu=shuchu.toString().substring(1,shuchu.toString().length()-1).split(",");
				 if(shu[0].equals("1"))
				 {
					 ret="1";
				 }
				 else if(shu[0].equals("0"))
				 {
					 ret=shu[1];
				 }
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
	  
	 public String kong(String shuju)
	 {
		 String[]shu=shuju.split(",");
		 String[]ret=new String[shu.length];
		 String re="";
		 int t=0;
		 for(String i:shu)
		 {
			 ret[t]=i.trim();		 
			 t++;
		 }
		 for(int i=0;i<ret.length;i++)
		 {
			 re+=ret[i]+",";
		 }
		 return re.substring(0, re.length()-1);
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
				 ret=DB(shu[0],shu[1],"device_frmpayload_data_temperature","time,dev_eui,device_name,value");
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
	 
		@SuppressWarnings("finally")
		public String setInsWorked(String insID,String operationToken,String insReq)
		{
			 SqlSession session = sessionFactory.openSession(); 	 
		     String start="me.gacl.mapping.userMapper.up_isFin_and_req";	
		     String ret="";
		     try {
				 instruction ins =new instruction();
				 ins.setInsID(insID);
				 ins.setOperationToken(operationToken);
				 SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss");
				 ins.setReq(df.format(new Date())+" "+insReq);
				 ins.setIsFin("1");
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
				 ret= "e:"+ex.toString();
				 ex.printStackTrace();
			 }
			 finally
			 {
				 session.close();
				 return ret;
			 }
		}
		
		 
		 @SuppressWarnings("finally")
			public  ArrayList<String> getUplinkRX1(String insID,String operationToken)
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
					 ret=DB(shu[0],shu[1],"device_uplink","time,dev_eui,device_name,dr,frequency,rssi,snr");
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
		 
		 
		 public ArrayList<String> DB(String devEui,String t,String tab,String sj)
		 {
			 ArrayList<String> ret=new ArrayList<String>();
			 String[] shuzhu=new String[2];
			 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss.SSSSSS");
			 String[] time_1=newtime(t).split(",");
			 String[] time_2=df.format(new Date()).split(",");
			 shuzhu[0]=time_1[0]+"T"+time_1[1]+"Z";
			 shuzhu[1]=time_2[0]+"T"+time_2[1]+"Z";
			 InfluxDB iDB=InfluxDBFactory.connect("http://47.101.172.221:8086", "admin", "admin");		
			 if(iDB==null)
			 {
				 ret.add("e:连接失败");
				 return ret;
			 }
			 String sqlcom="SELECT "+sj+" FROM "+tab+" where dev_eui = '"+devEui+"'and time>='"+shuzhu[0]+"' and time <='"+shuzhu[1]+"'";
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
		    		 			ret.add(kong(n.toString().substring(1,n.toString().length()-1)));
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
			public ArrayList<String> getDeviceRX2(String insID,String operationToken)
			 {
				 SqlSession session = sessionFactory.openSession(); 	 
			     String start="me.gacl.mapping.userMapper.select_devEui_and_t_and_et";	
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
						 String []time=new String[2];
						 String []t_1=newtime(shu[2],shu[1]).split(",");
						 String []t_2=edtime(shu[2]).split(",");
						 time[0]=t_1[0]+"T"+t_1[1]+"Z";
						 time[1]=t_2[0]+"T"+t_2[1]+"Z";
						 String sql="SELECT time,dev_eui,device_name,value FROM device_frmpayload_data_temperature where dev_eui = '"+shu[0]+"'and time>='"+time[0]+"' and time <='"+time[1]+"'";
						 ret=Db(sql);
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
		 
		 public String newtime(String ed_time,String t)
		 {
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss.SSSSSS");
		        // 将字符串的日期转为Date类型，ParsePosition(0)表示从第一个字符开始解析
		        Date date = sdf.parse(ed_time, new ParsePosition(0));
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        // add方法中的第二个参数n中，正数表示该日期后n天，负数表示该日期的前n天
		        calendar.add(Calendar.HOUR_OF_DAY, -(Integer.parseInt(t)));
				 String dateStr=df.format(calendar.getTimeInMillis());
				return dateStr;
		 }
		 
		 public String edtime(String ed_time)
		 {
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss.SSSSSS");
		        Date date = sdf.parse(ed_time, new ParsePosition(0));
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
				 String dateStr=df.format(calendar.getTimeInMillis());
				return dateStr;
		 }
		 
		 public ArrayList<String> Db(String sql)
		 {
			 ArrayList<String> ret=new ArrayList<String>();
			 InfluxDB iDB=InfluxDBFactory.connect("http://47.101.172.221:8086", "admin", "admin");		
			 if(iDB==null)
			 {
				 ret.add("e:连接失败");
				 return ret;
			 }
			 Query query=new Query(sql, "LoRaDB");
		     try {
		    	 QueryResult qs=iDB.query(query);
		    	 for(Result temp:qs.getResults())
		    	 	{ 
		    		 	List<Series> series = temp.getSeries();
		    		 	for(Series serie : series){
		    		 		List<List<Object>> values = serie.getValues();
		    		 		for(List<Object> n : values){
		    		 			ret.add(kong(n.toString().substring(1,n.toString().length()-1)));
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
}
