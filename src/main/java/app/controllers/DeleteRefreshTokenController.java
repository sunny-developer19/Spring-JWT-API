package app.controllers;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.dto.RefreshToken;
import app.exceptions.RefreshTokenNotFoundException;
import app.services.RefreshTokenService;
import app.utils.JWTtokenUtil;

@RestController
public class DeleteRefreshTokenController {

	@Autowired
	private RefreshTokenService refreshTokenService;
	

	@RequestMapping(method = RequestMethod.DELETE, path = "/access-tokens")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteRefreshToken(@RequestBody Map<String,Object> body) {
		String refreshTokenStr = body.get("refresh_token").toString();

		try {
			refreshTokenService.delete(refreshTokenStr);
		} catch (RefreshTokenNotFoundException e) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Refresh token = " + refreshTokenStr + " was not found!", 
			          new IllegalArgumentException());
		}
	}

	
	@RequestMapping(method = RequestMethod.POST, path = "/access-tokens/refresh")
	@ResponseStatus(code = HttpStatus.OK)
	public String getRefreshedJWTaccessToken(@RequestBody Map<String,Object> body) {
		JSONObject jsonObject = new JSONObject();
		
		String refreshTokenStr = body.get("refresh_token").toString();
		try {
			RefreshToken refreshTokFromDB = refreshTokenService.getById(refreshTokenStr);
			
			if (refreshTokFromDB != null) {
				String jwtAccessTokenStr = JWTtokenUtil.getJWTtokenFromUserEmail(refreshTokFromDB.getUserEmail());

				jsonObject.put("jwt", jwtAccessTokenStr);

			}
			else
			{
				jsonObject.put("ERROR", "Invalid Refresh Token");
			}

		} catch (JSONException e) {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "Request Body is not well formed!", 
			          new IllegalArgumentException());
			
		} catch (RefreshTokenNotFoundException e) {
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, "Refresh token = " + refreshTokenStr + " was not found!", 
			          new IllegalArgumentException());
		}
		
		return jsonObject.toString();
	}
	
	

	public RefreshTokenService getRefreshTokenService() {
		return refreshTokenService;
	}


	public void setRefreshTokenService(RefreshTokenService refreshTokenService) {
		this.refreshTokenService = refreshTokenService;
	}
}
