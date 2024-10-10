package kernel360.techpick.feature.folder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kernel360.techpick.core.model.folder.Folder;
import kernel360.techpick.core.model.folder.FolderType;
import kernel360.techpick.core.model.user.User;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {

	List<Folder> findAllByUserAndParentFolderIsNotNull(User user);

	List<Folder> findAllByUserAndParentFolderId(User user, Long parentFolderId);

	boolean existsByUserAndName(User user, String name);

	boolean existsByUserAndFolderType(User user, FolderType folderType);

	// QueryDSL 도입 후 리팩토링 필요
	@Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.folderType = kernel360.techpick.core.model.folder.FolderType.UNCLASSIFIED")
	Folder findUnclassifiedByUserId(@Param("userId") Long userId);

	Folder findByUserAndFolderType(User user, FolderType folderType);

	// QueryDSL 도입 후 리팩토링 필요
	@Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.folderType = kernel360.techpick.core.model.folder.FolderType.RECYCLE_BIN")
	Folder findRecycleBinByUserId(Long userId);

	// QueryDSL 도입 후 리팩토링 필요
	@Query("SELECT f FROM Folder f WHERE f.user.id = :userId AND f.folderType = kernel360.techpick.core.model.folder.FolderType.ROOT")
	Folder findRootByUserId(Long userId);
}
