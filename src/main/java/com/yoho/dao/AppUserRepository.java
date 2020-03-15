package com.yoho.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yoho.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	public AppUser findByUsername(String username);
}