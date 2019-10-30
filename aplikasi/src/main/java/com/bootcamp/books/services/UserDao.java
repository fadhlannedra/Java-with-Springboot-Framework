package com.bootcamp.books.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDao extends UserDetailsService {
	
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
