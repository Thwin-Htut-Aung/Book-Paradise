package com.book.paradise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.book.paradise.entity.Users;
import com.book.paradise.repo.UserRepo;

public class BookParadiseUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepo usersRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user = usersRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("This email cannot be found " + email));
		return new BookParadiseUserDetails(user);
	}

}
