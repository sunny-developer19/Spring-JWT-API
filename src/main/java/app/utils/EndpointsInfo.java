package app.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EndpointsInfo {

	private String errorMessage;
	private Map<String, String> validEndpoints;
	
	
	public EndpointsInfo() {
		errorMessage = "Resource Not Found. Not a valid endpoint.";
		validEndpoints = new HashMap<String, String>();
		validEndpoints.put("POST /users", "https://springbootmyideajwt.herokuapp.com/users");
		validEndpoints.put("POST /access-tokens", "https://springbootmyideajwt.herokuapp.com/access-tokens");
		
		validEndpoints.put("DELETE /acess-tokens", "https://springbootmyideajwt.herokuapp.com/access-tokens");
		validEndpoints.put("POST /access-tokens/refresh", "https://springbootmyideajwt.herokuapp.com/access-tokens/refresh");
		
		validEndpoints.put("POST /ideas", "https://springbootmyideajwt.herokuapp.com/ideas");
		validEndpoints.put("PUT /ideas/{id}", "https://springbootmyideajwt.herokuapp.com/ideas/{id}");
		validEndpoints.put("DELETE /ideas/{id}", "https://springbootmyideajwt.herokuapp.com/ideas/{id}");
		validEndpoints.put("GET /ideas", "https://springbootmyideajwt.herokuapp.com/ideas?page=<pageNum>");
		
		validEndpoints.put("GET /me", "https://springbootmyideajwt.herokuapp.com/me");
	}
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public Map<String, String> getValidEndpoints() {
		return validEndpoints;
	}
	public void setValidEndpoints(Map<String, String> validEndpoints) {
		this.validEndpoints = validEndpoints;
	}
	
}
