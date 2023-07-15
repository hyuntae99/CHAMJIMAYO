package com.project.chamjimayo.controller;

import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.controller.dto.SearchRequestDto;
import com.project.chamjimayo.controller.dto.SearchResponseDto;
import com.project.chamjimayo.security.CustomUserDetails;
import com.project.chamjimayo.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "search_address", description = "주소 검색 API")
@RequiredArgsConstructor
@RequestMapping("/address")
@RestController
public class SearchController {

	private final SearchService searchService;

	/**
	 * 검색어와 유저 id를 받아서 검색어에 대한 도로명 주소, 지번 주소, 가게 이름을 반환 searchAddress 의 count 변수로 조절 가능 예시:
	 * /address/search?searchWord={검색어}
	 */
	@Operation(summary = "검색", description = "검색어를 받고 검색 결과를 제공합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "검색 결과 반환",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "1. 파라미터가 부족합니다. \t\n2. 올바르지 않은 파라미터 값입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", description = "1. Api 응답이 올바르지 않습니다. \t\n2.Json 파일이 올바르지 않습니다. \t\n3.유저를 찾지 못했습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/search")
	public ResponseEntity<List<SearchResponseDto>> getAddress(
		@Parameter(description = "검색어", required = true, example = "신림역스타벅스")
		@RequestParam("searchWord") String searchWord,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 현재 로그인 한 유저의 userId를 가져옴
		Long userId = customUserDetails.getId();
		SearchRequestDto requestDTO = SearchRequestDto.create(searchWord, userId);
		List<SearchResponseDto> searchResponseDTOList = searchService.searchAddress(requestDTO);
		return ResponseEntity.ok(searchResponseDTOList);
	}


	/**
	 * 유저 아이디를 받아서 해당 유저의 최근 검색 기록 (도로명 주소, 지번 주소, 이름) 예시: /address/search/recent
	 */
	@Operation(summary = "최근 검색 기록", description = "최근 검색 기록을 반환합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "최근 검색 기록 반환",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponseDto.class))),
	})
	@GetMapping("/search/recent")
	public ResponseEntity<SearchResponseDto> getRecentAddress(
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		// 현재 로그인 한 유저의 userId를 가져옴
		Long userId = customUserDetails.getId();
		// userId를 통해 최근 검색 기록을 가져옴
		SearchResponseDto responseDTO = searchService.getRecentRoadAddress(userId);
		// 검색 결과를 ResponseEntity.ok() 메서드를 사용하여 성공적인 응답으로 반환
		return ResponseEntity.ok(responseDTO);
	}

	/**
	 * 도로명 주소를 클릭한 경우 해당 도로명 주소의 상태를 변경 -> 해당 주소를 클릭 처리하면 최종적으로 검색한 것으로 처리 예시:
	 * /address/search/click/{searchId}
	 */
	@Operation(summary = "클릭", description = "검색 기록을 클릭 처리합니다.(최근 검색 기록을 반환하기 위한 처리)")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "정상적으로 클릭이 되었습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = SearchResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "1. 파라미터가 부족합니다. \t\n2. 올바르지 않은 파라미터 값입니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
		@ApiResponse(responseCode = "404", description = "검색 기록을 찾을 수 없습니다.",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/search/click/{searchId}")
	public ResponseEntity<String> clickAddress(
		@Parameter(description = "검색 기록 ID", required = true, example = "1 (Long)")
		@PathVariable Long searchId) {
		// searchId를 받아서 클릭 처리
		return searchService.clickAddress(searchId);
	}
}


