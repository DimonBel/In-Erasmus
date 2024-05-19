package erasmus.networking.common.utils;

public class ConsoleOutputMessages {

  public static final String WELCOME_MESSAGE = "Welcome to the Grid Student management system!";

  public static final String CHOOSE_OPTION_MESSAGE = "Choose an option:";

  public static final String INPUT_YOUR_COMMAND_MESSAGE = "Input your command> ";

  public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void greetingMessage() {
    System.out.println(WELCOME_MESSAGE + "\n" + CHOOSE_OPTION_MESSAGE);
    System.out.println("g - general Operations");
    System.out.println("f - faculty Operations");
    System.out.println("q - exit the program");
  }

  public static void printStudentManagementMessage() {
    System.out.println("nf/<faculty name>/<faculty abbreviation>/<field> - create new faculty");
    System.out.println("df - display faculties");
    System.out.println("ss/<email> - search what faculty student belongs");
    System.out.println("dff/<field> - display all faculties of a field");
    System.out.println("bes - batch enroll students from file batch_enrollment.txt");
    System.out.println("bgs - batch graduation students from file batch_enrollment.txt");
    System.out.println();
    System.out.println("b - back");
    System.out.println("q - exit the program");
  }

  public static void facultyOperationMessage() {
    System.out.println(
        "ns/<faculty abbreviation>/<first name>/<last name>/<email>/<date-of-birth (dd-MM-yyyy)> - create new student");
    System.out.println("gs/<email> - (g)raduate (s)tudent");
    System.out.println("de/<faculty abbreviation> - (d)isplay (e)nrolled students");
    System.out.println("dg/<faculty abbreviation> - (d)isplay (g)raduated students");
    System.out.println(
        "bf/<faculty abbreviation>/<email> - check if student (b)elongs to (f)aculty");
    System.out.println();
    System.out.println("b - back");
    System.out.println("q - exit the program");
  }
}
