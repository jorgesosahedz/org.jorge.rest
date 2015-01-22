package org.jorge.rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
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
	public Response returnIDName(
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

	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnID(
			@PathParam("id") String id) 
					throws Exception{
	
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
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserIntoPerson(String incomingData) throws Exception {
		
		String returnString = null;
		//JSONArray jsonArray = new JSONArray(); //not needed
		Schema dao = new Schema();
		
		try {
			System.out.println("incomingData: " + incomingData);
			
			/*
			 * ObjectMapper is from Jackson Processor framework
			 * http://jackson.codehaus.org/
			 * 
			 * Using the readValue method, you can parse the json from the http request
			 * and data bind it to a Java Class.
			 */
			ObjectMapper mapper = new ObjectMapper();  
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);	
			
			int http_code = dao.insertIntoPerson(itemEntry.id, itemEntry.name, itemEntry.lastname, itemEntry.age, itemEntry.birthday);
			
			if( http_code == 200 ) {
				//returnString = jsonArray.toString();
				returnString = "Item inserted";
			} else {
				return Response.status(500).entity("Unable to process Item").build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}



class ItemEntry{
	
	public String id;
	public String name;
	public String lastname;
	public String age;
	public String birthday;
	
}
