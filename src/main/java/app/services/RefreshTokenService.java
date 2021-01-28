package app.services;

import app.dto.RefreshToken;
import app.exceptions.RefreshTokenNotFoundException;

public interface RefreshTokenService {

	RefreshToken getById(String refreshTokenStr) throws RefreshTokenNotFoundException;
	
	RefreshToken getByUserEmail(String email);

	RefreshToken save(RefreshToken refreshToken);
	

	void delete(String refreshTokenStr) throws RefreshTokenNotFoundException;
}
