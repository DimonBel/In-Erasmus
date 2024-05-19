package erasmus.networking.domain.model.repository.specifications;

import java.time.Instant;

import erasmus.networking.domain.model.entity.Faculty;
import org.springframework.data.jpa.domain.Specification;

public class FacultySpecifications {

  public static Specification<Faculty> nameContains(String name) {
    return (root, query, cb) -> {
      if (name == null || name.isEmpty()) return null;
      return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    };
  }

  public static Specification<Faculty> abbreviationContains(String abbreviation) {
    return (root, query, cb) -> {
      if (abbreviation == null || abbreviation.isEmpty()) return null;
      return cb.like(cb.lower(root.get("abbreviation")), "%" + abbreviation.toLowerCase() + "%");
    };
  }

  public static Specification<Faculty> universityIdEquals(Long universityId) {
    return (root, query, cb) -> {
      if (universityId == null) return null;
      return cb.equal(root.get("university").get("id"), universityId);
    };
  }

  public static Specification<Faculty> createdAfter(Instant date) {
    return (root, query, cb) -> {
      if (date == null) return null;
      return cb.greaterThanOrEqualTo(root.get("createdAt"), date);
    };
  }

  public static Specification<Faculty> createdBefore(Instant date) {
    return (root, query, cb) -> {
      if (date == null) return null;
      return cb.lessThanOrEqualTo(root.get("createdAt"), date);
    };
  }

  public static Specification<Faculty> combine(Specification<Faculty>... specs) {
    Specification<Faculty> result = Specification.where(specs[0]);
    for (int i = 1; i < specs.length; i++) {
      result = result.and(specs[i]);
    }
    return result;
  }
}
