package erasmus.networking.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseDate {

  public static final String enrollmentDatePattern = "dd-MM-yyyy";

  public static final String dateOfBirthPattern = "dd-MM-yyyy";

  public static Date parseStringToDate(String enrollmentDate, String pattern) {
    try {
      SimpleDateFormat enrollmentDateFormat = new SimpleDateFormat(pattern);
      return enrollmentDateFormat.parse(enrollmentDate);
    } catch (ParseException e) {
      System.out.println("Error while parsing date: " + e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  public static String parseDateToString(Date date, String pattern) {
    SimpleDateFormat enrollmentDateFormat = new SimpleDateFormat(pattern);
    return enrollmentDateFormat.format(date);
  }

  public static Date getCurrentDate() {
    return new Date();
  }
}
