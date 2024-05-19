package erasmus.networking.domain.model.repository.specifications;

import erasmus.networking.common.enums.SearchOperation;
import erasmus.networking.domain.model.entity.Program;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgramSpecificationBuilder {

    private final List<SearchCriteria> params = new ArrayList<>();

    public ProgramSpecificationBuilder with(String key, String operation, Object value) {
        if (value != null && !value.toString().isEmpty()) {
            SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
            if (op != null) {
                params.add(new SearchCriteria(key, op, convertToJavaType(key, value)));
            }
        }
        return this;
    }

    public Specification<Program> build() {
        if (params.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        Specification<Program> result = new ProgramSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new ProgramSpecification(params.get(i)));
        }

        return result;
    }

    private Object convertToJavaType(String key, Object value) {
        switch (key) {
            case "tripDate":
            case "createdAt":
            case "applicationDeadline":
                return parseDate(value.toString());
            default:
                return value;
        }
    }

    private Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use 'yyyy-MM-dd'.");
        }
    }
}
