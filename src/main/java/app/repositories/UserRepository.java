package app.repositories;
import app.dto.UserDetails;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDetails, String> {

}
