package kernel360.techpick.core.model.folder;

import java.util.EnumSet;

public enum FolderType {

	UNCLASSIFIED ("미분류 폴더"),
	RECYCLE_BIN ("휴지통 폴더"),
	ROOT ("루트 폴더"),
	GENERAL ("일반 폴더"),
	;

	private final String label;

	FolderType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public static EnumSet<FolderType> getBasicFolderTypes() {
		return EnumSet.of(UNCLASSIFIED, RECYCLE_BIN, ROOT);
	}

	public static EnumSet<FolderType> getUnclassifiedFolderTypes() {
		return EnumSet.of(GENERAL, RECYCLE_BIN, ROOT);
	}
}
