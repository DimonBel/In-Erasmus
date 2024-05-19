package erasmus.networking.domain.providers.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import erasmus.networking.domain.providers.DataSourceProvider;
import jakarta.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.SQLException;
import erasmus.networking.domain.providers.DataSourceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("hikariProvider")
public class HikariDataSourceProvider implements DataSourceProvider {
  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  private HikariDataSource dataSource;

  @PostConstruct
  public void init() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    config.setMaximumPoolSize(10);
    config.setPoolName("HikariPool22");
    dataSource = new HikariDataSource(config);
  }

  @Override
  public String provideDataSource() {
    return "HikariDataSource";
  }

  @Override
  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  public HikariDataSource getDataSource() {
    return dataSource;
  }
}
