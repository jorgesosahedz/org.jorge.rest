package org.jorge.rest.inventory;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.jorge.dao.MySQLRest;
import org.jorge.util.ToJSON;

@Path("/v1/inventory")
public class V1_inventory {
	
	
	//@Path("/database")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPersons() throws Exception{
	PreparedStatement query = null;
	String returnString = null;
	Connection conn = null;
	Response response =  null;
	
	try{
		conn = MySQLRest.getMySQLConn().getConnection();
		query = conn.prepareStatement("select id,name,lastname,age,birthday from person");
		ResultSet rs = query.executeQuery();
		
		
		ToJSON converter =  new ToJSON();
		JSONArray json = new JSONArray();
		
		json = converter.toJSONArray(rs);
		query.close();
		returnString = json.toString();
		response = Response.ok(returnString).build();
		
		
	}catch(Exception e){
		e.printStackTrace();	
	}
	finally{
		if (conn != null) query.close();
	}
	
	return response;
	

	}
}
