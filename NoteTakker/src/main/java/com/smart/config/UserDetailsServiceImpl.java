package com.smart.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.dao.PersonRepository;
import com.smart.entities.Person;

public class UserDetailsServiceImpl  implements UserDetailsService{
	@Autowired
	private PersonRepository personRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// fetching user from database
		
		Person person = personRepository.getUserByUserName(username);
		if(person==null) {
			throw new UsernameNotFoundException("could not found user");
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(person);
		return customUserDetails;
	}

	
	

}
