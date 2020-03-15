package com.yoho.service;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yoho.entities.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AccountService accountService;
	private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("loadUserByUsername " + username);

		AppUser u = accountService.findUserByUsername(username);
		if (u == null)
			throw new UsernameNotFoundException(username);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		u.getRoles().forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getRole()));
		});
		return new User(u.getUsername(), u.getPassword(), authorities);
	}
}