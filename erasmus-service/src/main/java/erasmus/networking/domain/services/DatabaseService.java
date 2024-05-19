package erasmus.networking.domain.services;

import java.sql.Connection;
import java.sql.SQLException;

import erasmus.networking.common.utils.DatabaseUtility;
import erasmus.networking.domain.providers.impl.HikariDataSourceProvider;
import erasmus.networking.domain.providers.impl.SingleConnectionDataSourceProvider;
import erasmus.networking.common.utils.DatabaseUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

  private final SingleConnectionDataSourceProvider singleConnectionProvider;

  private final HikariDataSourceProvider hikariDataSourceProvider;

  private final DatabaseUtility databaseUtility;

  @Autowired
  public DatabaseService(
      @Qualifier("singleConnectionProvider")
          SingleConnectionDataSourceProvider singleConnectionProvider,
      HikariDataSourceProvider hikariDataSourceProvider,
      DatabaseUtility databaseUtility) {
    this.singleConnectionProvider = singleConnectionProvider;
    this.hikariDataSourceProvider = hikariDataSourceProvider;
    this.databaseUtility = databaseUtility;
  }

  public void executeLongRunningAction() {

    try (Connection conn = hikariDataSourceProvider.getConnection()) {
      conn.createStatement().execute("SELECT pg_sleep(1)");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Single: Long running action executed");
    }
  }

  public void executeLongRunningActionSingle() {
    try (Connection conn = singleConnectionProvider.getConnection()) {
      conn.createStatement().execute("SELECT pg_sleep(1)");
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Long running action executed");
    }
  }

  public String inconsistentEnrollment() {
    try {
      String addStudentSql =
          "INSERT INTO students (faculty_id, first_name, last_name, email, date_of_birth) VALUES (?, ?, ?, ?, ?)";
      databaseUtility.execute(
          addStudentSql,
          1,
          "Inconsistent",
          "Enrollment",
          "john.doe@example.com",
          new java.util.Date());

      throw new Exception("Simulated error after adding student");

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      System.out.println("Inconsistent enrollment executed");
    }
    return "Enrollment process completed (but check for consistency!)";
  }

  public String consistentEnrollment() {
    Connection conn = null;
    try {
      conn = databaseUtility.getConnection();
      conn.setAutoCommit(false);

      String addStudentSql =
          "INSERT INTO students (faculty_id, first_name, last_name, email, date_of_birth) VALUES (?, ?, ?, ?, ?)";
      databaseUtility.execute(
          addStudentSql, 1, "Eduard", "Smelov", "test@test.com", new java.util.Date());

      String updateFacultySql = "UPDATE faculties SET updated_at = CURRENT_TIMESTAMP WHERE id = ?";
      databaseUtility.execute(updateFacultySql, 1);

      throw new Exception("Simulated error");

    } catch (Exception e) {
      e.printStackTrace();
      try {
        if (conn != null) {
          conn.rollback();
          return "Failed to enroll student consistently: Transaction rolled back due to an error: "
              + e.getMessage();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
        return "Failed to enroll student consistently and error during rollback: "
            + ex.getMessage();
      }
    } finally {
      try {
        if (conn != null) {
          conn.setAutoCommit(true);
          conn.close();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
    return "An unexpected error occurred, and the system could not handle your request.";
  }
}
