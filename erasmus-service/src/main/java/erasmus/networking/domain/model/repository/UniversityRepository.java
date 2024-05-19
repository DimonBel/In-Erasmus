package erasmus.networking.domain.model.repository;

import erasmus.networking.api.responses.university.UniversityResponse;
import erasmus.networking.domain.model.entity.University;
import erasmus.networking.api.responses.university.UniversityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Long> {
  @Query("""
          SELECT new erasmus.networking.api.responses.university.UniversityResponse(u.id, u.name) 
          FROM University u 
          WHERE u.name = :name
           """)
  Optional<UniversityResponse> findUniversityRecordByName(@Param("name") String name);

  @Query("""
          SELECT new erasmus.networking.api.responses.university.UniversityResponse(u.id, u.name) 
          FROM University u 
          WHERE u.id = :id
           """)
  Optional<UniversityResponse> findUniversityRecordById(@Param("id") Long id);
}
