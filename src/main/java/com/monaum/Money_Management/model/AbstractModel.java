package com.monaum.Money_Management.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractModel<U> implements Serializable {

	@CreatedBy
	@Column(name = "createdBy", length = 25)
	private U createdBy;

	@LastModifiedBy
	@Column(name = "updatedBy", length = 25)
	private U updatedBy;

	@CreationTimestamp
	@Column(name = "createdAt", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updatedAt")
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime updatedAt;
}
