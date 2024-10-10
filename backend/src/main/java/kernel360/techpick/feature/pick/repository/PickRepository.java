package kernel360.techpick.feature.pick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.techpick.core.model.folder.FolderType;
import kernel360.techpick.core.model.link.Link;
import kernel360.techpick.core.model.pick.Pick;
import kernel360.techpick.core.model.user.User;

@Repository
public interface PickRepository extends JpaRepository<Pick, Long> {

	boolean existsByLink(Link link);

	List<Pick> findAllByUser(User user);

	List<Pick> findAllByUserAndParentFolderId(User user, Long parentFolderId);

	List<Pick> findAllByUserAndParentFolderFolderType(User user, FolderType folderType);

	boolean existsByUserIdAndLinkUrl(Long userId, String url);
}
