package com.project.chamjimayo.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Integer reviewId;

	// 회원 아이디 (해당 리뷰를 어떤 회원이 썼는가)
	@Column(name = "user_id")
	private Integer userId;

	// 화장실 아이디 (어떤 화장실에 대한 리뷰인가)
	@Column(name = "restroom_id")
	private Integer restroomId;

	// 리뷰 내용
	@Column(name = "review_content")
	private String reviewContent;

	// 별점 (0 ~ 5점)
	@Min(0)
	@Max(5)
	@Column(name = "rating")
	private Float rating;

	// 생성일
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	// 수정일
	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	// 리뷰 상태
	@Pattern(regexp = "[01]")
	@Column(name = "review_status")
	private boolean reviewStatus;
}
