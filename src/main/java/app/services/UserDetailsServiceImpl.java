package app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.dto.UserDetails;
import app.repositories.UserRepository;

import static java.util.Collections.emptyList;

import java.util.NoSuchElementException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private UserRepository userDetailsRepository;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository)
	{
		this.userDetailsRepository = userRepository;
	}

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	UserDetails applicationUser = null;
    	try{
    		applicationUser = userDetailsRepository.findById(email).get();
    	}
    	catch(NoSuchElementException ex) {
    		throw new UsernameNotFoundException(email);
    	}
       
        return new User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList());
    }
}