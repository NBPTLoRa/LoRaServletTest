package lora.influx;

import java.util.List;

import org.influxdb.InfluxDB;
import org.influxdb.*;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;

import com.google.gson.JsonObject;

public class influxDBQuery {

	InfluxDB iDB;
	public influxDBQuery() throws ClassNotFoundException
	{
		
		iDB=InfluxDBFactory.connect("http://167.179.83.38:8086", "admin", "admin");
		if(iDB!=null)
		{
			System.out.println("DATABASEConnectSuccess!!!!!"); 
		}else
		{
			System.out.println("DATABASEConnectFialed!!!!!"); 
		}
	}
	
	public JsonObject queryAllinTable(String table) 
	{
		String database="LoRaDB";
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
				jsonObject.addProperty("colums", colums.toString());
				for(List<Object> n : values){
					System.out.println("value:" + n);
					jsonObject.addProperty("value",n.toString());
				}
			}
		}
		return jsonObject;
	}
}
