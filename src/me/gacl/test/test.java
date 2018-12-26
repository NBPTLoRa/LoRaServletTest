package me.gacl.test;

import lora.sqloperation.Sql;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sql sql=new Sql();
    	System.out.println(sql.getDevProfIDforProfName("999"));
    	System.out.println(sql.getDevProfIDforProfName("smoke"));
	}

}
