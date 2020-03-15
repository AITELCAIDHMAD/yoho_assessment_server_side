package com.yoho.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppRole extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -97257114368795716L;
	@Id
	@GeneratedValue
	private Long id;
	private String role;
}
