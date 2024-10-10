package kernel360.techpick.feature.pick.service.dto;

import java.util.List;

import kernel360.techpick.feature.link.service.dto.LinkResponse;
import kernel360.techpick.feature.link.service.dto.LinkUrlResponse;
import kernel360.techpick.feature.tag.service.dto.TagResponse;

public record PickResponse(
	Long id,
	String title,
	String memo,
	Long folderId,
	Long userId,
	List<TagResponse> tagList,
	LinkUrlResponse linkUrlResponse
) {
}
