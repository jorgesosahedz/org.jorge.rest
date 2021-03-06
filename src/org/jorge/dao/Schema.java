package org.jorge.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONArray;
import org.jorge.util.ToJSON;

public class Schema {

	public int insertIntoPerson(	String id, 
			String name, 
			String lastname, 
			String age, 
			String birthday	) 
					throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before starting to insert data into the database.
			 * 
			 * Important: The primary key on PC_PARTS table will auto increment.
			 * 		That means the PC_PARTS_PK column does not need to be apart of the 
			 * 		SQL insert query below.
			 * 
			 */
			conn =  MySQLRest.getMySQLConn().getConnection();
			query = conn.prepareStatement("insert into person " +
					"(ID, NAME, LASTNAME, AGE, BIRTHDAY) " +
					"VALUES ( ?, ?, ?, ?, ? ) ");

			int myId = Integer.parseInt(id);
			query.setInt(1, myId);

			query.setString(2, name);
			query.setString(3, lastname);
			int myAge = Integer.parseInt(age);			
			query.setInt(4, myAge);
			query.setString(5, birthday);
			query.execute();

		} catch(Exception e) {
			e.printStackTrace();
			return 406; //if a error occurs, return a 500
		}
		finally {
			if (conn != null) conn.close();
		}

		return 200;
	}


	public JSONArray returnAllPersons(@QueryParam("id") String id) throws Exception{
		PreparedStatement query = null;
		Connection conn = null;		
		ToJSON converter =  new ToJSON();
		JSONArray json = new JSONArray();

		try{
			conn = MySQLRest.getMySQLConn().getConnection();
			query = conn.prepareStatement("select id, name, lastname, age, birthday from person where id = ?");
			query.setString(1, id);
			ResultSet rs = query.executeQuery();			
			json = converter.toJSONArray(rs);
			query.close();			

		}catch(SQLException e){
			e.printStackTrace();	
			return json;
		}
		finally{
			if (conn != null) query.close();
		}

		return json;
	}


	public JSONArray returnByIdName(@QueryParam("id") String id, @QueryParam("name") String name) throws Exception{
		PreparedStatement query = null;
		Connection conn = null;		
		ToJSON converter =  new ToJSON();
		JSONArray json = new JSONArray();

		try{
			conn = MySQLRest.getMySQLConn().getConnection();
			query = conn.prepareStatement("select id, name, lastname, age, birthday from person where id = ? and name = ?");
			query.setString(1, id);
			query.setString(2, name);
			ResultSet rs = query.executeQuery();			
			json = converter.toJSONArray(rs);
			query.close();			

		}catch(SQLException e){
			e.printStackTrace();	
			return json;
		}
		finally{
			if (conn != null) query.close();
		}

		return json;
	}

	public int updatePerson(String id, String lastname) throws Exception {

		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before updating data.
			 */

			conn = MySQLRest.getMySQLConn().getConnection();
			query = conn.prepareStatement("update person " +
					"set lastname = ? " +
					"where id = ? ");

			query.setString(1, lastname);
			query.setInt(2, Integer.parseInt(id));
			query.executeUpdate();

		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}

		return 200;
	}


	public int deletePerson(String id) throws Exception  {
		PreparedStatement query = null;
		Connection conn = null;

		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before updating data.
			 */

			conn = MySQLRest.getMySQLConn().getConnection();
			query = conn.prepareStatement("delete from person " +
					"where id = ? ");
			query.setInt(1, Integer.parseInt(id));
			query.executeUpdate();

		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}

		return 200;
	}
}
