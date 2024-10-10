package kernel360.techpick.feature.folder.model;

import org.springframework.stereotype.Component;

import kernel360.techpick.core.model.folder.Folder;
import kernel360.techpick.core.model.folder.FolderType;
import kernel360.techpick.core.model.user.User;
import kernel360.techpick.feature.folder.exception.ApiFolderException;
import kernel360.techpick.feature.folder.service.dto.FolderCreateRequest;
import kernel360.techpick.feature.folder.service.dto.FolderResponse;

@Component
public class FolderMapper {

	public Folder toFolderEntity(User user, FolderType folderType, FolderCreateRequest request) {
		switch (folderType) {
			case ROOT -> {
				return Folder.rootFolder(request.name(), user);
			}
			case RECYCLE_BIN -> {
				return Folder.recycleBinFolder(request.name(), user);
			}
			case UNCLASSIFIED -> {
				return Folder.unclassifedFolder(request.name(), user);
			}

			case GENERAL -> {
				return Folder.generalFolder(request.name(), user);
			}

			default -> throw ApiFolderException.INVALID_FOLDER_TYPE();
		}
	}

	public FolderResponse toFolderResponse(Folder folder) {
		return new FolderResponse(
			folder.getId(),
			folder.getName(),
			folder.findParentFolderId(),
			folder.getUser().getId()
		);
	}
}
