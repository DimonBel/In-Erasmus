package erasmus.networking.domain.model.repository;

import java.util.List;
import java.util.Optional;

import erasmus.networking.common.enums.StudyField;
import erasmus.networking.domain.model.entity.Faculty;
import erasmus.networking.domain.model.entity.StudyFieldEntity;
import erasmus.networking.common.enums.StudyField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository
    extends JpaRepository<Faculty, Long>, JpaSpecificationExecutor<Faculty> {

  @Query("SELECT f FROM Faculty f WHERE f.abbreviation = ?1")
  Faculty findFacultyByAbbreviation(String abbreviation);

  @Query("SELECT f FROM Faculty f JOIN f.students s WHERE s.email = ?1")
  Optional<Faculty> findFacultyBelongingToStudent(String email);

  @Query("SELECT f FROM Faculty f JOIN f.studyField sf WHERE sf.name = ?1")
  Optional<List<Faculty>> findAllFacultiesByStudyFieldName(StudyField studyFieldName);

  @Query("SELECT sf FROM Faculty f JOIN f.studyField sf WHERE sf.name = :name")
  StudyFieldEntity findStudyFieldByName(@Param("name") StudyField name);
}
