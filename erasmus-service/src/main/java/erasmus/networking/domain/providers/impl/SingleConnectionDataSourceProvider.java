package erasmus.networking.domain.providers.impl;

import java.sql.*;

import erasmus.networking.domain.providers.DataSourceProvider;
import lombok.Setter;
import erasmus.networking.domain.providers.DataSourceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("singleConnectionProvider")
@Setter
public class SingleConnectionDataSourceProvider implements DataSourceProvider {

  private Connection conn;

  @Value("${spring.datasource.url}")
  private String urlF;

  @Value("${spring.datasource.username}")
  private String usernameF;

  @Value("${spring.datasource.password}")
  private String passworF;

  @Override
  public String provideDataSource() {
    return "SingleConnectionDataSource";
  }

  @Override
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(urlF, usernameF, passworF);
  }
}
