package me.gacl.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
		 System.out.println(sql.getUplinkRXLast("d896e0ff00000253"));
	}

}
 