package app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.dto.JWTtoken;
import app.dto.RefreshToken;
import app.dto.UserDetails;
import app.services.RefreshTokenService;
import app.services.UserService;
import app.utils.JWTtokenUtil;
import app.utils.PasswordValidatorUtil;
import app.utils.RefreshTokenGeneratorUtil;
import app.utils.UserBasicDetails;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<UserDetails> getAllUsers()
	{
		return userService.listAll();
	}

	@RequestMapping(method = RequestMethod.POST, path = "/users")
	@ResponseStatus(code = HttpStatus.CREATED)
	@Consumes(value="appliction/json")
	@Produces(value="appliction/json")
	public JWTtoken addUser(@RequestBody UserDetails user) {
		String password = user.getPassword();
		if (!PasswordValidatorUtil.isPasswordValid(password) || password.length() < 8) {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "Password shpuld be at least 8 characters and should be composes on at lease one upper, lower case letter and a number", 
			          new IllegalArgumentException());
		}

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.saveOrUpdate(user);

		String refreshTokenStr = RefreshTokenGeneratorUtil.getRefreshToken();
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(refreshTokenStr);
		refreshToken.setUserEmail(user.getEmail());

		refreshTokenService.save(refreshToken);

		JWTtoken jwt = new JWTtoken();
		jwt.setJwt(JWTtokenUtil.getJWTtokenFromUser(user));
		jwt.setRefresh_token(refreshTokenStr);
		return jwt;
	}	

	@RequestMapping(method = RequestMethod.GET, path = "/me")
	@ResponseStatus(code = HttpStatus.OK)
	@Produces(value="appliction/json")
	public String getCurrentUser(@RequestHeader(name = "X-Access-Token") String jwtTokenStr)
	{
		String userEmail = JWTtokenUtil.getUserFromToken(jwtTokenStr);
		if (userEmail != null)
		{
			JSONObject jsonObject = new JSONObject();
			UserDetails user = userService.getById(userEmail);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			UserBasicDetails userBasicDetails = new UserBasicDetails();
			
			userBasicDetails.setName(user.getName());
			userBasicDetails.setEmail(user.getEmail());
			userBasicDetails.setAvatar_url(user.getAvatar_url());
						
			return gson.toJson(userBasicDetails);
		}

		return null;
	}

	public BCryptPasswordEncoder getbCryptPasswordEncoder() {
		return bCryptPasswordEncoder;
	}

	public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public RefreshTokenService getRefreshTokenService() {
		return refreshTokenService;
	}

	public void setRefreshTokenService(RefreshTokenService refreshTokenService) {
		this.refreshTokenService = refreshTokenService;
	}
}
