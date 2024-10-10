package kernel360.techpick.feature.pick.model;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.techpick.core.model.pick.PickTag;
import kernel360.techpick.core.model.tag.Tag;
import kernel360.techpick.feature.tag.service.dto.TagResponse;

@Component
public class PickTagMapper {

	// Tag Mapper로 옮기는 것이 좋을지 의문
	public List<TagResponse> toTagResponse(List<PickTag> pickTags) {
		return pickTags.stream()
			.map(pickTag -> {
				Tag tag = pickTag.getTag();
				return new TagResponse(tag.getId(), tag.getName(), tag.getTagOrder(), tag.getColorNumber(),
					tag.getUser().getId());
			})
			.toList();
	}
}
