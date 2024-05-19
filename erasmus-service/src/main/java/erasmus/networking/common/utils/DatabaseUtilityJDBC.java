package erasmus.networking.common.utils;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtilityJDBC {

  private final JdbcTemplate jdbcTemplate;

  public DatabaseUtilityJDBC(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void execute(String sql, Object... args) {
    jdbcTemplate.update(sql, args);
  }

  public <T> T findOne(String sql, RowMapper<T> rowMapper, Object... args) {
    return jdbcTemplate.queryForObject(sql, args, rowMapper);
  }

  public <T> List<T> findMany(String sql, RowMapper<T> rowMapper, Object... args) {
    return jdbcTemplate.query(sql, args, rowMapper);
  }

  public int queryForInt(String sql, Object... args) {
    return jdbcTemplate.queryForObject(sql, Integer.class, args);
  }
}
