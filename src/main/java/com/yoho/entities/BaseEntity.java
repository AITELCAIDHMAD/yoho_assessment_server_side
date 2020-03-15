package com.yoho.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@MappedSuperclass
public abstract class BaseEntity {
	@Column(updatable = false)
	@CreationTimestamp
	@JsonIgnore
	private LocalDateTime createdAt;
	@UpdateTimestamp
	@JsonIgnore
	private LocalDateTime updatedAt;
}