package kernel360.techpick.feature.folder.model;

import org.springframework.stereotype.Component;

import kernel360.techpick.core.model.folder.Folder;
import kernel360.techpick.core.model.folder.FolderType;
import kernel360.techpick.core.model.user.User;
import kernel360.techpick.feature.folder.service.dto.FolderCreateRequest;
import kernel360.techpick.feature.folder.service.dto.FolderResponse;
import kernel360.techpick.feature.user.UserRepository;
import kernel360.techpick.feature.user.exception.ApiUserException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FolderMapper {

	private final UserRepository userRepository;

	public Folder createFolder(FolderCreateRequest request) throws ApiUserException {
		User user = userRepository.findById(request.getUserId()).orElseThrow(ApiUserException::USER_NOT_FOUND);
		return Folder.create(request.getName(), FolderType.GENERAL, user);
	}

	public FolderResponse createResponse(Folder folder) {
		return new FolderResponse(
			folder.getId(),
			folder.getName(),
			folder.findParentFolderId(),
			folder.getUser().getId()
		);
	}
}
