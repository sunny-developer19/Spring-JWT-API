package app.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.RefreshToken;
import app.exceptions.RefreshTokenNotFoundException;
import app.repositories.RefreshTokenRepository;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

	@Autowired
	private RefreshTokenRepository refreshTokenRepo;
	
	
	@Override
	public RefreshToken getById(String refreshTokenStr) throws RefreshTokenNotFoundException {
		RefreshToken tok = null;
		try {
			tok = refreshTokenRepo.findById(refreshTokenStr).get();
		}
		catch (NoSuchElementException ex) {
			throw new RefreshTokenNotFoundException("Refresh token = " + refreshTokenStr + " was not found!");
		}
		
		return tok;
	}

	@Override
	public RefreshToken save(RefreshToken refreshToken) {
		
		RefreshToken rTok = refreshTokenRepo.save(refreshToken);
		return rTok;
	}

	@Override
	public void delete(String refreshTokenStr) throws RefreshTokenNotFoundException {
		RefreshToken rTok = getById(refreshTokenStr);
		
		refreshTokenRepo.deleteById(refreshTokenStr);
		
	}

	public RefreshTokenRepository getRefreshTokenRepo() {
		return refreshTokenRepo;
	}

	public void setRefreshTokenRepo(RefreshTokenRepository refreshTokenRepo) {
		this.refreshTokenRepo = refreshTokenRepo;
	}

	@Override
	public RefreshToken getByUserEmail(String email) {
		
		return refreshTokenRepo.findByEmail(email);
	}

}
