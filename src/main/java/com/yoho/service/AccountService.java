package com.yoho.service;

import com.yoho.entities.AppRole;
import com.yoho.entities.AppUser;

public interface AccountService {
	public AppUser saveUser(AppUser u);

	public AppRole saveRole(AppRole r);

	public AppUser findUserByUsername(String username);

	public void addRoleToUser(String username, String role);
}
