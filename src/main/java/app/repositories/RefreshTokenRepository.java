package app.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import app.dto.Idea;
import app.dto.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String>{

	 RefreshToken findByEmail(String email);
}
