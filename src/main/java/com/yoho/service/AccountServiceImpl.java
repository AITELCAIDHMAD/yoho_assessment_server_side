package com.yoho.service;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yoho.dao.AppRoleRepository;
import com.yoho.dao.AppUserRepository;
import com.yoho.entities.AppRole;
import com.yoho.entities.AppUser;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);
	@Autowired
	private AppUserRepository userRepository;
	@Autowired
	private AppRoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public AppUser saveUser(AppUser u) {
		logger.info("saveUser " + u);
		u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
		return userRepository.save(u);
	}

	@Override
	public AppRole saveRole(AppRole r) {
		logger.info("saveRole " + r);
		return roleRepository.save(r);
	}

	@Override
	public AppUser findUserByUsername(String username) {
		logger.info("findUserByUsername " + username);
		return userRepository.findByUsername(username);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		logger.info("addRoleToUser " + username + "  roleName" + roleName);
		AppUser user = userRepository.findByUsername(username);
		AppRole role = roleRepository.findByRole(roleName);

		System.out.println("user => " + user);
		System.out.println("role => " + role);
		user.getRoles().add(role);
	}
}