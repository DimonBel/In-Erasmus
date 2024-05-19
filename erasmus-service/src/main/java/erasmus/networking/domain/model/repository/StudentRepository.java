package erasmus.networking.domain.model.repository;

import java.util.List;

import erasmus.networking.domain.model.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

  @Query("SELECT s FROM Student s WHERE s.faculty.id = ?1")
  List<Student> findStudentsByFacultyId(Long facultyId);

  @Query("SELECT s FROM Student s WHERE s.email = ?1")
  Student findStudentByEmail(String email);

  @Query(
      """
        SELECT s
        FROM Student s
        WHERE (:firstName IS NULL OR s.firstName LIKE %:firstName%)
          AND (:lastName IS NULL OR s.lastName LIKE %:lastName%)
          AND (:isGraduated IS NULL OR s.isGraduated = :isGraduated)
    """)
  Page<Student> searchStudents(
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("isGraduated") Boolean isGraduated,
      Pageable pageable);
}
