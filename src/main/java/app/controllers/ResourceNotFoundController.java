package app.controllers;

import javax.ws.rs.Produces;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.utils.EndpointsInfo;

@RestController
public class ResourceNotFoundController {

	@RequestMapping(value = "/**")
	@Produces("application/json")
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public EndpointsInfo getResponseForNoResourceFound() {
		return new EndpointsInfo();
		
	}
	
	
}
