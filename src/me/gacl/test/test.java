package me.gacl.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lora.sqloperation.Sql;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Result;
import org.influxdb.dto.QueryResult.Series;


import com.google.gson.JsonObject;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sql sql=new Sql();
		ArrayList<String> shu=sql.getDeviceRX1("F2FF879B72F572B99984A10646ABDF8C","8F788478CFCC868605739289E5AFBF95");
		for(String i:shu)
		{
			System.out.println(i);
		}
	}

}
