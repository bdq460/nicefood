package com.egghead.nicefood.accessdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author zhangjun.zyk
 * @since 2013-8-3 02:32:22
 * 
 */
public class Accessor {

	public static void main(String[] args) throws Exception {
		access();
	}

	public static void access() throws Exception {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		String url = "jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=D:\\personal\\learn\\weixin\\projects\\nicefood.mdb";
		Connection conn = DriverManager.getConnection(url);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM content limit 10");
		System.out.println("Got ResultSet Now");
		// rs.beforeFirst();
		while (rs.next()) {
			System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t"
					+ rs.getString(3));
		}
		rs.close();

		stmt.close();
		conn.close();
	}
}
