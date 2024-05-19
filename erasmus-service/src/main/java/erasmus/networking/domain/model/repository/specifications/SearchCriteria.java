package erasmus.networking.domain.model.repository.specifications;

import erasmus.networking.common.enums.SearchOperation;
import lombok.Getter;
import lombok.Setter;
import erasmus.networking.common.enums.SearchOperation;

import java.io.Serializable;

@Getter
@Setter
public class SearchCriteria implements Serializable {
  private String key;
  private SearchOperation operation;
  private Object value;
  private boolean orPredicate;

  public SearchCriteria(String key, SearchOperation operation, Object value) {
    this.key = key;
    this.operation = operation;
    this.value = value;
  }

}
