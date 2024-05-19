package erasmus.networking.domain.model.repository;

import erasmus.networking.domain.model.entity.Achievements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievements, Long> {

}
