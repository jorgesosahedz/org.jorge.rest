package org.jorge.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.jorge.dao.Schema;

@Path("/v3/inventory")
public class V3_inventory {
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserIntoPerson(String incomingData) throws Exception {
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray(); 
		JSONObject jsonObject = new JSONObject();
		Schema dao = new Schema();
		
		try {			
			JSONObject personData = new JSONObject(incomingData);
			System.out.println("jsonData: " + personData.toString());
			
			int http_code = dao.insertIntoPerson(personData.optString("id"),personData.optString("name"),personData.optString("lastname"),personData.optString("age"),personData.optString("birthday"));
			
			if( http_code == 200 ) {
				jsonObject.put("HTTP_CODE", "200");
				jsonObject.put("MSG", "Item entered successfuly, version 3");
				returnString = jsonArray.put(jsonObject).toString();
				
			} else {
				
				return Response.status(Status.NOT_ACCEPTABLE).entity("Invalid user entry").build();				
			}
			
			System.out.println("returnString: " + returnString.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("HTTP_CODE", "500");
			jsonObject.put("MSG", "Unable to process your request");
			returnString = jsonArray.put(jsonObject).toString();			
		}
		
		return Response.ok(returnString).build();
	}
}

