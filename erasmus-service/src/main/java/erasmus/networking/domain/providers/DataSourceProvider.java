package erasmus.networking.domain.providers;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSourceProvider {
  String provideDataSource();

  Connection getConnection() throws SQLException;
}
