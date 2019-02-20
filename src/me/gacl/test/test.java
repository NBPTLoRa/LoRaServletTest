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
		ArrayList<String> a=new  ArrayList<String> ();
		 a=sql.getUplinkRX2("7A3EE254EC2412CFAE9D2700D96584BB","C17EF5DF91C3E293EB08D370F13B85AF");
		for(String i:a)
		System.out.println(i);
	}

}
