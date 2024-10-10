package kernel360.techpick.feature.pick.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel360.techpick.core.model.pick.PickTag;

// TODO: tag 삭제 시 pick이 존재하는지 여부 확인을 위해 임시로 PickTagRepository 생성
// 이후 PickTagProvider 가 구현될 경우 책임 이관 예정
@Repository
public interface PickTagRepository extends JpaRepository<PickTag, Long> {

	boolean existsByTagId(Long tagId);

	void deleteByTagId(Long tagId);

	void deleteByPickIdAndTagId(Long pickId, Long tagId);

	List<PickTag> findAllByPickId(Long pickId);
}
