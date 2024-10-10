package kernel360.techpick.feature.pick.model;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import kernel360.techpick.core.model.folder.Folder;
import kernel360.techpick.core.model.link.Link;
import kernel360.techpick.core.model.pick.Pick;
import kernel360.techpick.core.model.pick.PickTag;
import kernel360.techpick.core.model.tag.Tag;
import kernel360.techpick.core.model.user.User;
import kernel360.techpick.feature.link.model.LinkMapper;
import kernel360.techpick.feature.link.service.dto.LinkUrlResponse;
import kernel360.techpick.feature.pick.service.dto.PickCreateRequest;
import kernel360.techpick.feature.pick.service.dto.PickResponse;
import kernel360.techpick.feature.tag.model.TagMapper;
import kernel360.techpick.feature.tag.model.TagProvider;
import kernel360.techpick.feature.tag.service.dto.TagResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PickMapper {

	public Pick toPickEntity(User user, Link link, Folder folder, PickCreateRequest request) {
		return Pick.create(user, link, folder, request.title(), request.memo());
	}

	public PickResponse toPickResponse(Pick pick, List<TagResponse> tagResponseList, LinkUrlResponse linkUrlResponse) {
		return new PickResponse(pick.getId(), pick.getCustomTitle(), pick.getMemo(), pick.getParentFolder().getId(),
			pick.getUser().getId(), tagResponseList, linkUrlResponse);
	}

	public List<PickResponse> toPickResponseList(List<Pick> pickList, PickTagProvider pickTagProvider,
		PickTagMapper pickTagMapper, LinkMapper linkMapper) {
		return pickList.stream().map(pick -> {
			List<PickTag> pickTagList = pickTagProvider.findAllPickTagByPickId(pick.getId());
			List<TagResponse> tagResponseList = pickTagMapper.toTagResponse(pickTagList);
			LinkUrlResponse linkUrlResponse = linkMapper.toLinkUrlResponse(pick.getLink());

			return this.toPickResponse(pick, tagResponseList, linkUrlResponse);
		}).toList();
	}

	// Tag Mapper로 이동해야 할 지 의문
	public List<TagResponse> toTagResponseList(Pick pick, List<Long> tagIdList, List<PickTag> pickTagList,
		PickTagProvider pickTagProvider, TagProvider tagProvider, TagMapper tagMapper) {

		return tagIdList.stream()
			.map(tagId -> createTagResponse(pick, tagId, pickTagList, tagProvider, tagMapper, pickTagProvider))
			.toList();
	}

	// Tag 유무 확인 후 없으면 생성, 있으면 기존 것 반환
	private TagResponse createTagResponse(Pick pick, Long tagId, List<PickTag> pickTagList, TagProvider tagProvider,
		TagMapper tagMapper, PickTagProvider pickTagProvider) {
		// 기존 태그가 존재하는지 확인
		PickTag existingPickTag = pickTagList.stream()
			.filter(pickTag -> pickTag.getTag().getId().equals(tagId))
			.findFirst()
			.orElse(null);

		if (Objects.isNull(existingPickTag)) {
			Tag tag = tagProvider.findById(tagId);
			pickTagProvider.save(PickTag.create(pick, tag));
			return tagMapper.toTagResponse(tag);
		}

		return tagMapper.toTagResponse(existingPickTag.getTag());
	}
}
