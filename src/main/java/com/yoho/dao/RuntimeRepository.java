package com.yoho.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RuntimeRepository extends JpaRepository<com.yoho.entities.Runtime, Long> {
}