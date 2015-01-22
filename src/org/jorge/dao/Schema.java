package org.jorge.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.QueryParam;
import org.codehaus.jettison.json.JSONArray;
import org.jorge.util.ToJSON;

public class Schema {

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
}
