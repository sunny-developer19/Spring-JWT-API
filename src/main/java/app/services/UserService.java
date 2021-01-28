package app.services;

import java.util.List;

import app.dto.UserDetails;

public interface UserService {

    List<UserDetails> listAll();

    UserDetails getById(String userEmail);

    UserDetails saveOrUpdate(UserDetails user);

    void delete(String userEmail);


}
