package com.project.chamjimayo.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequestDto {

  @Schema(type = "Long", example = "1")
  @NotNull(message = "화장실 ID를 입력해주세요.")
  private final Long restroomId;

  @Schema(type = "string", example = "깔끔해요!")
  @NotBlank(message = "리뷰 내용을 입력해주세요.")
  private final String reviewContent;

  @Schema(type = "Integer", example = "4")
  @NotNull(message = "평점을 입력해주세요.")
  @Min(value = 0, message = "평점은 0 ~ 5점으로 입력해주세요.")
  @Max(value = 5, message = "평점은 0 ~ 5점으로 입력해주세요.")
  private final Integer rating;

  private ReviewRequestDto(Long restroomId, String reviewContent, Integer rating) {
    this.restroomId = restroomId;
    this.reviewContent = reviewContent;
    this.rating = rating;
  }

  public static ReviewRequestDto create(Long restroomId, String reviewContent, Integer rating) {
    return new ReviewRequestDto(restroomId, reviewContent, rating);
  }
}
