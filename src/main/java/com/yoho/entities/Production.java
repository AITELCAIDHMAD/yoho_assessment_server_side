package com.yoho.entities;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Production {
	@Id
	@GeneratedValue
	private Long id;
	private String machine_name;
	private String variable_name;
	private String datetime_from;
	private  String datetime_to;
	private double value;
}
