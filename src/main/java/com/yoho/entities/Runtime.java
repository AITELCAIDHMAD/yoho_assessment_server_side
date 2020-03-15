package com.yoho.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Runtime {
	@Id
	@GeneratedValue
	private Long id;
	private String machine_name;
	private Date datetime;
	private Boolean isrunning;
}
