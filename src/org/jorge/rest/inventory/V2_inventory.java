package org.jorge.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.jorge.dao.MySQLRest;
import org.jorge.dao.Schema;
import org.jorge.util.ToJSON;

@Path("/v2/inventory")
public class V2_inventory {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPersons(@QueryParam("id") String id) throws Exception{
	
	String returnString = null;	
	JSONArray json = null;
	
	try{
		
		if (id == null){
			return Response.status(400).entity("Error. Specify an ID").build();
		}
		Schema dao =  new Schema();
		json = dao.returnAllPersons(id);
		returnString = json.toString();
		
	}catch(Exception e){
		e.printStackTrace();	
		return Response.status(500).entity("Error. Not able to process request").build();
	}	
	
	return Response.ok(returnString).build();
	

	}
	
	@Path("/{id}/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnID(
			@PathParam("id") String id,
			@PathParam("name") String name) 
					throws Exception{
	
	String returnString = null;	
	JSONArray json = null;
	
	try{
		
		if (id == null){
			return Response.status(400).entity("Error. Specify an ID").build();
		}
		Schema dao =  new Schema();
		json = dao.returnByIdName(id, name);
		returnString = json.toString();
		
	}catch(Exception e){
		e.printStackTrace();	
		return Response.status(500).entity("Error. Not able to process request").build();
	}	
	
	return Response.ok(returnString).build();
	

	}


}
