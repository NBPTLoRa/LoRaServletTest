package lora.influx;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import com.google.gson.JsonObject;


class Test{
	public static void main(String[] args)
	{
		ArrayList<String> ret=new ArrayList<String>();
		//long stime=new Date().getTime();
		InfluxDB iDB;
		iDB=InfluxDBFactory.connect("http://47.101.172.221:8086", "admin", "admin");//注释
		if(iDB!=null)
		{
			System.out.println("DATABASEConnectSuccess!!!!!"); 
		}else
		{
			System.out.println("DATABASEConnectFialed!!!!!"); 
		}
		String database="LoRaDB";
		String sqlcom="select * from  device_uplink order by time desc limit 1";
		JsonObject jsonObject=new JsonObject();
		Query query=new Query(sqlcom, database);
		QueryResult qs=iDB.query(query);
		for(Result temp:qs.getResults())
		{
			List<Series> series = temp.getSeries();
			for(Series serie : series){
				List<List<Object>> values = serie.getValues();
				for(List<Object> n : values){
					System.out.println(n.toString().substring(1,n.toString().length()-1));
				}
			}
		}
	}
	
}
