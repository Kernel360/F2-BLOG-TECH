package kernel360.techpick.feature.pick.validator;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import kernel360.techpick.core.model.pick.Pick;
import kernel360.techpick.core.model.user.User;
import kernel360.techpick.feature.pick.exception.ApiPickException;
import kernel360.techpick.feature.pick.model.PickProvider;
import kernel360.techpick.feature.pick.service.dto.PickCreateRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PickValidator {

	private final PickProvider pickProvider;

	public void validatePickAccess(User user, Pick pick) {
		if (ObjectUtils.notEqual(user, pick.getUser())) {
			throw ApiPickException.PICK_UNAUTHORIZED_ACCESS();
		}
	}

	// 픽 생성 시 해당 링크가 존재하는 지 검증
	public boolean validateExistLink(Long userId, PickCreateRequest pickCreateRequest) {
		return pickProvider.existsByUserIdAndLinkUrl(userId, pickCreateRequest.linkRequest().url());
	}
}