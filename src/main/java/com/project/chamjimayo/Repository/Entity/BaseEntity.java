package com.project.chamjimayo.Repository.Entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Getter
@MappedSuperclass
public abstract class BaseEntity {

	// 등록 시간 (생성 시간)
	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	private LocalDateTime createdDate;

	// 수정 시간
	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	private LocalDateTime updatedDate;

	// 상태
	@Column(name = "status")
	private boolean status;

}
