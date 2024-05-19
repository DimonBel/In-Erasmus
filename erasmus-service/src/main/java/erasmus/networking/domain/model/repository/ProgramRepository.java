package erasmus.networking.domain.model.repository;

import erasmus.networking.domain.model.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long>, JpaSpecificationExecutor<Program> {

    @Query("SELECT p FROM Program p JOIN p.student s WHERE s.id = :studentId")
    Optional<Program> getProgramsByStudentId(@Param("studentId") Long studentId);
}
