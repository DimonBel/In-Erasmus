package erasmus.networking.domain.model.repository.specifications;

import erasmus.networking.domain.model.entity.Student;

public class StudentSpecification extends GenericSpecification<Student> {

    public StudentSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}