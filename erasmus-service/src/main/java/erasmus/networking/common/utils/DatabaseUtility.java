package erasmus.networking.common.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import erasmus.networking.domain.providers.DataSourceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtility {

  private final DataSourceProvider dataSource;

  public DatabaseUtility(@Qualifier("singleConnectionProvider") DataSourceProvider dataSource) {
    this.dataSource = dataSource;
  }

  // a
  public void execute(String query, Object... args) {
    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {

      for (int i = 0; i < args.length; i++) {
        if (args[i] instanceof java.util.Date) {
          java.util.Date utilDate = (java.util.Date) args[i];
          java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
          stmt.setDate(i + 1, sqlDate);
        } else {
          stmt.setObject(i + 1, args[i]);
        }
      }
      stmt.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // b
  public void execute(String query, Consumer<PreparedStatement> consumer) {
    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {

      consumer.accept(stmt);
      stmt.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // d
  public <T> T findOne(String query, Function<ResultSet, T> mapper, Object... args)
      throws SQLException {
    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {

      for (int i = 0; i < args.length; i++) {
        stmt.setObject(i + 1, args[i]);
      }

      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        if (rs.isLast()) {
          return mapper.apply(rs);
        } else {
          throw new SQLException("Query returned more than one result");
        }
      }
      return null;
    }
  }

  // e
  public <T> List<T> findMany(String query, Function<ResultSet, T> mapper, Object... args) {
    List<T> results = new ArrayList<>();
    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {

      for (int i = 0; i < args.length; i++) {
        stmt.setObject(i + 1, args[i]);
      }

      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        results.add(mapper.apply(rs));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return results;
  }

  public int queryForInt(String query, Object... args) throws SQLException {
    try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {
      for (int i = 0; i < args.length; i++) {
        stmt.setObject(i + 1, args[i]);
      }
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getInt(1);
      } else {
        throw new SQLException("No result returned for queryForInt");
      }
    }
  }

  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}
