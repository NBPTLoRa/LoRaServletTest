package lora.influx;

import java.util.Date;
import java.util.List;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import com.google.gson.JsonObject;


public class Test{
	public static void main(String[] args)
	{
		long stime=new Date().getTime();
		InfluxDB iDB;
		iDB=InfluxDBFactory.connect("http://167.179.83.38:8086", "admin", "admin");
		if(iDB!=null)
		{
			System.out.println("DATABASEConnectSuccess!!!!!"); 
		}else
		{
			System.out.println("DATABASEConnectFialed!!!!!"); 
		}
		String database="LoRaDB";
		String table="cpu";
		String sqlcom="SELECT * FROM "+table;
		JsonObject jsonObject=new JsonObject();
		Query query=new Query(sqlcom, database);
		QueryResult qs=iDB.query(query);
		jsonObject.addProperty("TableName", table);
		for(Result temp:qs.getResults())
		{
			List<Series> series = temp.getSeries();
			for(Series serie : series){
				List<List<Object>> values = serie.getValues();//×Ö¶Î×Ö¼¯ºÏ
				List<String> colums = serie.getColumns();//×Ö¶ÎÃû
				System.out.println("colums:" + colums);
				for(List<Object> n : values){
					System.out.println("value:" + n.toString());
					jsonObject.addProperty("value",n.toString() );
				}
			}
		}
	}
	
}
