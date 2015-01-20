package org.jorge.rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.sql.*;
import org.jorge.dao.*;

/**
 * @author jesus.sosa
 *
 */
@Path("/v1/status")
public class V1_status {
	
	private static final String ver = "00.01.00";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle(){
		return "<p>Java Web Service</p>";
	}
	
	@Path ("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion(){
		return "<p>Version:"+ver+"</p>";
	}
	
	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDataBaseStatus() throws Exception{
		PreparedStatement query = null;
		String myString = null;
		String returnString = null;
		Connection conn = null;
		
		try{
			conn = MySQLRest.getMySQLConn().getConnection();
			query = conn.prepareStatement("select curtime() as 'mydatetime'");
			ResultSet rs = query.executeQuery();
			
			while (rs.next()){
				myString = rs.getString("mydatetime");
			}
			query.close();
			
			returnString = "<p> Database status </p>"+
							"<p> Database Date/time return:"+ myString + "</p>";
			
			
		}catch(Exception e){
			e.printStackTrace();
			myString = "January 19, 2015 Blue Monday";
		}
		finally{
			if (conn != null) query.close();
		}
		
		return returnString;
	}


}
