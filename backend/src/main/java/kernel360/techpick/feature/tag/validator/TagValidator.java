package kernel360.techpick.feature.tag.validator;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Component;

import kernel360.techpick.core.exception.feature.tag.ApiTagException;
import kernel360.techpick.core.model.tag.Tag;
import kernel360.techpick.feature.tag.model.TagProvider;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagValidator {

	private final TagProvider tagProvider;

	public void validateTagAccess(Long userId, Tag tag) throws ApiTagException {

		if (tag == null || !Objects.equals(userId, tag.getUser().getId())) {
			throw ApiTagException.UNAUTHORIZED_TAG_ACCESS();
		}
	}

	public void validateTagNameExists(Long userId, String name) throws ApiTagException {

		if (tagProvider.existsByUserIdAndName(userId, name)) {
			throw ApiTagException.TAG_ALREADY_EXIST();
		}
	}

	public void validateTagOrder(Map<Long, Tag> tagMap) throws ApiTagException {

		Set<Integer> orderSet = new HashSet<>();

		for (Tag tag : tagMap.values()) {
			// 중복되거나 음수면 유효하지 않은 tag order
			if (!orderSet.add(tag.getOrder()) || tag.getOrder() < 0) {
				throw ApiTagException.TAG_INVALID_ORDER();
			}

		}

	}
}

