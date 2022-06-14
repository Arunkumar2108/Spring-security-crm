package com.mahd.employee.service;

import java.util.ArrayList;
import java.util.Collection;


import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import com.mahd.employee.models.User;
import com.mahd.employee.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
@NoArgsConstructor
public class UserService implements UserDetailsService {
	
	@Autowired
	private  UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Running Service");
		log.info("{}",username);
		User user = userRepo.findByUserName(username);
		log.info("User:{}",user);
		String id = Long.toString(user.getId());
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(id));
		user.getRole().getScopes().forEach(scope->{
			authorities.add(new SimpleGrantedAuthority(scope.getScopeName()));
		});
		log.info("Authorities:{}",authorities);
		return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);
	
	}

	public User getUserById(Authentication authentication){
		String userId = authentication.getAuthorities().iterator().next().toString();
		Long id = Long.parseLong(userId);
		User user = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not found for this id:"+id));
		return user;
	}
	

	
}
