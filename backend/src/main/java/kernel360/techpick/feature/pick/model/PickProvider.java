package kernel360.techpick.feature.pick.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kernel360.techpick.core.model.folder.FolderType;
import kernel360.techpick.core.model.pick.Pick;
import kernel360.techpick.feature.pick.exception.ApiPickException;
import kernel360.techpick.feature.pick.repository.PickRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PickProvider {

	private final PickRepository pickRepository;

	public Pick findById(Long pickId) {
		return pickRepository.findById(pickId).orElseThrow(ApiPickException::PICK_NOT_FOUND);
	}

	public List<Pick> findAllByUserId(Long userId) {
		return pickRepository.findAllByUserId(userId);
	}

	public List<Pick> findAllByParentFolderId(Long userId, Long parentFolderId) {
		return pickRepository.findAllByUserIdAndParentFolderId(userId, parentFolderId);
	}

	public List<Pick> findAllByUnclassified(Long userId) {
		return pickRepository.findAllByUserIdAndParentFolderFolderType(userId, FolderType.UNCLASSIFIED);
	}

	public Map<Long, Pick> findAllByUserIdAsMap(Long userId) {
		return findAllByUserId(userId).stream()
			.collect(Collectors.toMap(Pick::getId, Function.identity()));
	}

	public boolean existsByUserIdAndLinkUrl(Long userId, String url) {
		return pickRepository.existsByUserIdAndLinkUrl(userId, url);
	}

	public Pick save(Pick pick) {
		return pickRepository.save(pick);
	}

	public void deleteById(Long pickId) {
		pickRepository.deleteById(pickId);
	}
}
