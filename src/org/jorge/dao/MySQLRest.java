package org.jorge.dao;

import java.sql.Connection;

import javax.naming.*;
import javax.sql.*;

public class MySQLRest {

		private static DataSource MySQLRest =  null;
		private static Context context =  null;
		
		public static DataSource getMySQLConn() throws Exception{
		
			if (MySQLRest !=null){
				return MySQLRest;
			}
			
			
			if (context == null){
				context = new InitialContext();
				MySQLRest = (DataSource) context.lookup("mysql_rest");
			}									
			
			return MySQLRest;
		}
		
		protected static Connection mysqlPersonsConnection(){
			Connection conn = null;
			try{
				conn = MySQLRest.getConnection();
				return conn;
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			
			return conn;
		}
		
}
