package kernel360.techpick.feature.folder.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel360.techpick.core.model.folder.Folder;
import kernel360.techpick.feature.folder.exception.ApiFolderException;
import kernel360.techpick.feature.folder.model.FolderMapper;
import kernel360.techpick.feature.folder.model.FolderProvider;
import kernel360.techpick.feature.folder.service.dto.FolderCreateRequest;
import kernel360.techpick.feature.folder.service.dto.FolderResponse;
import kernel360.techpick.feature.folder.service.dto.FolderUpdateRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolderService {

	private final FolderProvider folderProvider;
	private final FolderMapper folderMapper;

	@Transactional
	public FolderResponse createFolder(Long userId, FolderCreateRequest request) throws ApiFolderException {

		// 생성하려는 폴더의 부모가 본인 폴더인지 검증
		Folder parent = folderProvider.findById(request.parentId());
		validateFolderAccess(userId, parent);

		// 생성하려는 폴더 이름이 중복되는지 검증
		validateDuplicateFolderName(userId, request.name());

		Folder folder = folderProvider.save(folderMapper.createFolder(userId, request, parent));

		return folderMapper.createResponse(folder);
	}

	@Transactional(readOnly = true)
	public List<FolderResponse> getFolderListByUserId(Long userId) {

		return folderProvider.findAllByUserId(userId)
			.stream()
			.map(folderMapper::createResponse)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<FolderResponse> getFolderListByParentId(Long userId, Long parentId) {

		return folderProvider.findAllByUserIdAndParentId(userId, parentId)
			.stream()
			.map(folderMapper::createResponse)
			.toList();
	}

	@Transactional
	public void updateFolder(Long userId, FolderUpdateRequest request) throws ApiFolderException {

		// 변경하려는 폴더가 본인 폴더인지 검증
		Folder targetFolder = folderProvider.findById(request.id());
		validateFolderAccess(userId, targetFolder);

		// 변경하려는 폴더의 부모가 본인 폴더인지 검증
		Folder parent = folderProvider.findById(request.parentId());
		validateFolderAccess(userId, parent);

		// 수정하려는 폴더 이름이 중복되는지 검증
		validateDuplicateFolderName(userId, request.name());

		targetFolder.update(request.name(), parent);
		folderProvider.save(targetFolder);
	}

	@Transactional
	public void moveToUnclassified(Long userId, Long folderId) {

		// 이동하려는 폴더가 본인 폴더인지 검증
		Folder targetFolder = folderProvider.findById(folderId);
		validateFolderAccess(userId, targetFolder);

		targetFolder.updateParent(folderProvider.findUnclassified(userId));
		folderProvider.save(targetFolder);
	}

	@Transactional
	public void moveToRecycleBin(Long userId, Long folderId) {

		// 이동하려는 폴더가 본인 폴더인지 검증
		Folder targetFolder = folderProvider.findById(folderId);
		validateFolderAccess(userId, targetFolder);

		targetFolder.updateParent(folderProvider.findRecycleBin(userId));
		folderProvider.save(targetFolder);
	}

	@Transactional
	public void deleteFolder(Long userId, Long folderId) throws ApiFolderException {

		// 삭제하려는 폴더가 본인 폴더인지 검증
		Folder targetFolder = folderProvider.findById(folderId);
		validateFolderAccess(userId, targetFolder);

		// TODO: 삭제된 폴더안의 다른 폴더와 픽들의 부모 설정은 FolderPickFacade에서 미분류로 변경
		folderProvider.deleteById(folderId);
	}

	private void validateFolderAccess(Long userId, Folder folder) throws ApiFolderException {

		if (folder == null || Objects.equals(userId, folder.getUser().getId())) {
			throw ApiFolderException.FOLDER_ACCESS_DENIED();
		}
	}

	private void validateDuplicateFolderName(Long userId, String name) throws ApiFolderException {

		if (folderProvider.existsByUserIdAndName(userId, name)) {
			throw ApiFolderException.FOLDER_ALREADY_EXIST();
		}
	}
}
