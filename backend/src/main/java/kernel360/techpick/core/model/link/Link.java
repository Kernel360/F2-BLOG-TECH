package kernel360.techpick.core.model.link;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "link")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Link {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * -------------------------------------------------------------------
	 * TODO:
	 * 들어온 URL String을 파싱해서 쿼리를 분리 저장 할지, 통으로 할지 토의 필요합니다.
	 * 만약 한다면, Spring의 UriComponent Class를 이용하면 될 것 같습니다.
	 * 지금은 쿼리를 따로 저장해야 할 이유를 못 찾았습니다.
	 * - origin
	 * - path
	 * - query
	 * - etc...
	 * 참고: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/util/UriComponents.html
	 * ---------------------------------------------------------------------
	 * NOTE:
	 * 링크에 대한 Title, Description은 프론트엔드에서 로딩합니다.
	 * - 제목이나 설명은 바뀔 수 있기 때문에 동적으로 그때 가져와야 하며, 굳이 DB에 저장할 필요가 없음
	 */

	// URL
	// TODO: VARCHAR 최대 크기를 몇으로 할지 토의 필요합니다.
	// 		 The index key prefix length limit is 3072 bytes for InnoDB -> VARCHAR(1000) + utf8 = 4000byte
	//       일단 medium 기준 가장 길었던 url 320 글자의 약 2배인 VARCHAR(600)으로 변경
	//       Techpick 노션 기술 부채에 VARCHAR, TEXT 부분 참고.
	@Column(name = "url", nullable = false, columnDefinition = "VARCHAR(600)", unique = true)
	private String url;

	@Column(name = "title", nullable = false, columnDefinition = "VARCHAR(100)")
	private String title;

	@Column(name = "description", columnDefinition = "VARCHAR(600)")
	private String description;

	@Column(name = "imageUrl", columnDefinition = "VARCHAR(600)")
	private String imageUrl;

	// TODO: 엔티티 사용자가 정적 팩토리 메소드로 필요한 함수를 구현 하세요
	// RSS 또는 관리자가 직접 등록 시 사용
	public static Link create(String url, String title, String description) {
		return new Link(url, title, description, null);
	}

	public static Link create(String url, String title, String description, String imageUrl) {
		return new Link(url, title, description, imageUrl);
	}

	public void updateLink(String title, String description, String imageUrl) {
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	private Link(String url, String title, String description, String imageUrl) {
		this.url = url;
		this.title = title;
		this.description = description;
		this.imageUrl = imageUrl;
	}
}
