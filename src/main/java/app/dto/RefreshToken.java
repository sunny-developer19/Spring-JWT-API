package app.dto;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RefreshToken {

	@Id
	@Column(name="token")
	private String refresh_token;
	@Column(unique=true, name="email")
	private String email;
	
	
	public String getToken() {
		return refresh_token;
	}
	public void setToken(String tokenStr) {
		this.refresh_token = tokenStr;
	}
	public String getUserEmail() {
		return email;
	}
	public void setUserEmail(String email) {
		this.email = email;
	}
	
	
}
