package kernel360.techpick.core.model.pick;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kernel360.techpick.core.model.common.TimeTracking;
import kernel360.techpick.core.model.folder.Folder;
import kernel360.techpick.core.model.link.Link;
import kernel360.techpick.core.model.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "pick")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pick extends TimeTracking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	// 사용자
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	// 북마크 대상 링크
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "link_id", nullable = false)
	private Link link;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "parent_folder_id", nullable = false)
	private Folder parentFolder;

	/**
	 * NOTE: 사용자가 설정한 커스텀 제목
	 * [#1] 사용자가 저장시 제목을 설정 하지 않았을 경우
	 *     1. 프론트에서 메타 데이터 조회
	 *     2. 있으면 메타에서 제목 가져오기
	 *     3. 없으면 쌩 URL을 제목으로 쓰기
	 *     4. 사용자가 추후에 제목을 자신만의 이름으로 변경 가능
	 *
	 * #2. 사용자가 저장시 제목을 설정 했을 경우
	 *     1. 이걸 보여준다.
	 */
	@Column(name = "custom_title") // nullable
	private String customTitle;

	// 사용자가 링크에 대해 남기는 메모
	@Column(name = "memo") // nullable
	private String memo;

	// TODO: 엔티티 사용자가 정적 팩토리 메소드로 필요한 함수를 구현 하세요

	private Pick(User user, Link link, Folder parentFolder, String customTitle, String memo) {
		this.user = user;
		this.link = link;
		this.parentFolder = parentFolder;
		this.customTitle = customTitle;
		this.memo = memo;
	}

	public static Pick create(User user, Link link, Folder parentFolder, String customTitle, String memo) {
		return new Pick(user, link, parentFolder, customTitle, memo);
	}

	public void updateParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}

	public void updatePick(String title, String memo) {
		this.customTitle = title;
		this.memo = memo;
	}
}
