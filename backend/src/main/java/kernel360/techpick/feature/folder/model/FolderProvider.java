package kernel360.techpick.feature.folder.model;

import java.util.EnumSet;
import java.util.List;

import org.springframework.stereotype.Component;

import kernel360.techpick.core.model.folder.Folder;
import kernel360.techpick.core.model.folder.FolderType;
import kernel360.techpick.core.model.user.User;
import kernel360.techpick.feature.folder.exception.ApiFolderException;
import kernel360.techpick.feature.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FolderProvider {

	private final FolderRepository folderRepository;

	public Folder save(Folder folder) {
		return folderRepository.save(folder);
	}

	public Folder findById(Long folderId) {
		return folderRepository.findById(folderId).orElseThrow(ApiFolderException::FOLDER_NOT_FOUND);
	}

	public List<Folder> findAllByUserAndParentFolderIsNotNull(User user) {
		return folderRepository.findAllByUserAndParentFolderIsNotNull(user);
	}

	public List<Folder> findAllByUserAndParentFolderId(User user, Long parentFolderId) {
		return folderRepository.findAllByUserAndParentFolderId(user, parentFolderId);
	}

	public Folder findUnclassified(User user) {
		return folderRepository.findUnclassifiedByUserId(user.getId());
	}

	public Folder findUnclassifiedByUser(User user) {
		return folderRepository.findByUserAndFolderType(user, FolderType.UNCLASSIFIED);
	}

	public Folder findRecycleBin(User user) {
		return folderRepository.findRecycleBinByUserId(user.getId());
	}

	public Folder findRoot(User user) {
		return folderRepository.findRootByUserId(user.getId());
	}

	public void deleteById(Long folderId) {
		folderRepository.deleteById(folderId);
	}

	public boolean existsByUserAndName(User user, String name) {
		return folderRepository.existsByUserAndName(user, name);
	}

	public boolean existsByUserAndFolderType(User user, FolderType folderType) {
		return folderRepository.existsByUserAndFolderType(user, folderType);
	}

	// 현재 폴더가 어떤 BasicFolder에 속해있는지 반환하는 함수
	// TODO: Spring Data Jpa에서 재귀쿼리를 제공하지 않아 반복조회로 해결.. QueryDSL 도입 후 리팩토링 필요
	public FolderType findParentBasicFolderById(Long folderId) {

		EnumSet<FolderType> basicFolderTypes = FolderType.getBasicFolderTypes();
		Folder currentFolder = this.findById(folderId);

		while (!basicFolderTypes.contains(currentFolder.getFolderType())) {
			currentFolder = currentFolder.getParentFolder();
		}

		return currentFolder.getFolderType();
	}
}
