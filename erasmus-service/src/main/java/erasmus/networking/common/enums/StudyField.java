package erasmus.networking.common.enums;

public enum StudyField {
  JAVA_DEVELOPMENT,
  JAVA_QA,
  JS_QA,
  DEVOPS,
  DATA_SCIENCE,
  CYBER_SECURITY,
  ARTIFICIAL_INTELLIGENCE,
  SOFTWARE_ENGINEERING,
  INFORMATION_SYSTEMS;

  public static StudyField fromValue(String value) {
    for (StudyField studyField : StudyField.values()) {
      if (studyField.name().equalsIgnoreCase(value)) {
        return studyField;
      }
    }
    return null;
  }

  public static String toValue(StudyField studyField) {
    return studyField.name();
  }
}
