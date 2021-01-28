package app.services;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.util.UserDataAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dto.UserDetails;
import app.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	public List<UserDetails> listAll() {
		// TODO Auto-generated method stub
		List<UserDetails> users = new ArrayList<UserDetails>();
		
		userRepository.findAll().forEach(users::add); 
		
		return users;
	}

	@Override
	public UserDetails getById(String userEmail) {
		// TODO Auto-generated method stub
		return userRepository.findById(userEmail).get();
	}

	@Override
	public UserDetails saveOrUpdate(UserDetails user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
		return user;
	}

	@Override
	public void delete(String userEmail) {
		// TODO Auto-generated method stub
		
		userRepository.deleteById(userEmail);
	}

}
