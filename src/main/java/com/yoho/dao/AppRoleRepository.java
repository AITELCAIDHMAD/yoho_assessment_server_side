package com.yoho.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yoho.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
	public AppRole findByRole(String role);
}