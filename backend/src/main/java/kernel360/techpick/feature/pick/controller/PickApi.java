package kernel360.techpick.feature.pick.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kernel360.techpick.core.model.folder.FolderType;
import kernel360.techpick.feature.pick.service.dto.PickCreateRequest;
import kernel360.techpick.feature.pick.service.dto.PickResponse;
import kernel360.techpick.feature.pick.service.dto.PickUpdateRequest;

@Tag(name = "픽 API", description = "픽 API")
public interface PickApi {

	@Operation(
		summary = "픽 상세 조회",
		description = "하나의 픽에 대한 상세 정보를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "픽 상세 정보 조회에 성공하였습니다."
		)
	})
	ResponseEntity<PickResponse> getPickById(Long pickId);

	@Operation(
		summary = "사용자 픽 리스트 조회",
		description = "사용자가 가진 모든 픽 리스트를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "사용자 픽 리스트 조회에 성공하였습니다."
		)
	})
	ResponseEntity<List<PickResponse>> getPickListByUser();

	@Operation(
		summary = "폴더에 있는 픽 리스트 조회",
		description = "해당 폴더에 있는 모든 픽 리스트를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "폴더 픽 리스트 조회에 성공하였습니다."
		)
	})
	ResponseEntity<List<PickResponse>> getPickListByParentFolderId(Long parentFolderId);

	@Operation(
		summary = "미분류 폴더에 있는 픽 리스트 조회",
		description = "미분류 폴더에 있는 모든 픽 리스트를 조회합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "미분류 폴더 픽 리스트 조회에 성공하였습니다."
		)
	})
	ResponseEntity<List<PickResponse>> getPickListByUnclassified(FolderType folderType);

	@Operation(
		summary = "픽 생성",
		description = "픽을 생성합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "픽 생성에 성공하였습니다."
		)
	})
	ResponseEntity<PickResponse> createPick(PickCreateRequest pickCreateRequest);

	@Operation(
		summary = "픽 수정",
		description = "픽을 수정합니다. (제목, 내용, 태그)"
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "픽 수정에 성공하였습니다."
		)
	})
	ResponseEntity<PickResponse> updatePick(PickUpdateRequest pickUpdateRequest);

}
