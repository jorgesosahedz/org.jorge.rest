package org.jorge.dao;

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
}
