package erasmus.networking.common.utils;

public final class ApiDescriptions {

  private ApiDescriptions() {
    throw new UnsupportedOperationException("Utility class");
  }

  public static final String TAG_FACULTY_OPERATIONS = "Faculties Operations";
  public static final String TAG_FACULTY_OPERATIONS_DESCRIPTION = "Operations related to faculties";

  public static final String SUMMARY_CREATE_FACULTY = "Create a new faculty";
  public static final String DESC_CREATE_FACULTY = "Create a new faculty with the provided data";

  public static final String SUMMARY_UPDATE_FACULTY = "Update faculty";
  public static final String DESC_UPDATE_FACULTY = "Update faculty with the provided data";

  public static final String SUMMARY_DELETE_FACULTY = "Delete faculty";
  public static final String DESC_DELETE_FACULTY = "Delete faculty with the provided ID";

  public static final String SUMMARY_GET_FACULTIES_BY_ID = "Get faculty by ID";
  public static final String DESC_GET_FACULTIES_BY_ID = "Get faculty by ID";
  public static final String SUMMARY_SEARCH_STUDENT_BELONGING_TO_FACULTY =
      "Search for a student's faculty";
  public static final String DESC_SEARCH_STUDENT_BELONGING_TO_FACULTY =
      "Returns the faculty a student belongs to based on their email";

  public static final String SUMMARY_IS_STUDENT_BELONGS_TO_FACULTY =
      "Check if student belongs to a faculty";
  public static final String DESC_IS_STUDENT_BELONGS_TO_FACULTY =
      "Checks if the given student belongs to the specified faculty using email and faculty abbreviation";

  public static final String SUMMARY_DISPLAY_FACULTIES = "Display all faculties";
  public static final String DESC_DISPLAY_FACULTIES =
      "Displays all faculties with their respective students with pagination and sorting options";

  public static final String SUMMARY_DISPLAY_FACULTIES_BY_FIELD =
      "Display faculties by study field";
  public static final String DESC_DISPLAY_FACULTIES_BY_FIELD =
      "Displays faculties filtered by study field";

  public static final String SUMMARY_SEARCH_FACULTIES = "Search faculties";
  public static final String DESC_SEARCH_FACULTIES =
      "Search faculties based on name, abbreviation, and university ID with pagination and sorting";
}
