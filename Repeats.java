
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * We use this class as the main class
 * which calls either the mode1,mode2 or
 * mode3 class depending
 * on the mode dictated by the input
 */
class Repeats {

  static String text1 = "";

  /**
   * This is the main method of the program which is
   * used to create the object for
   * each class depending on the arguments
   *
   * @param args String []
   * @throws IOException to catch error
   * @throws NumberFormatException to catch error
   */
public static void main(String[] args)
throws NumberFormatException, IOException {

    int mode = Integer.parseInt(args[0]);
    Repeats rep = new Repeats();

    switch (mode) {

      case 1:

        Scanner sc = null;
        String text = "";
        try {
          sc = new Scanner(new File(args[1]));
          if (sc.hasNext()) {
            text = sc.next();
            text1 = text;
          }
          sc.close();
        } catch (FileNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        rep.errorChecks(args, mode);
        Mode1 m1 = new Mode1(args[1], text);

        break;
      case 2:

        rep.errorChecks(args, mode);
        Mode2 m2 = new Mode2(Integer.parseInt(args[1]),
            Integer.parseInt(args[2]),
            args[3].charAt(0));

        break;
      case 3:

        rep.errorChecks(args, mode);
        Mode3 m3 = new Mode3(Integer.parseInt(args[1]),
            args[2].charAt(0),
            Double.parseDouble(args[3]));

        break;
      default:
        rep.errorChecks(args, mode);
    }
  }

  /**
   * This method creates the textfile
   * containing the output; used for mode 2
   *
   * @param suffix String containing the suffix of the file name
   * @param prefix String containing the prefix of the file name
   */
  private static void createOutFile(String suffix, String prefix) {

    File file = new File("THIS MUST BE FILE PATH " + prefix 
    + "**number**" + suffix);
    try {
      file.createNewFile();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * This method performs all the error checks.
   *
   * @param args String []
   * @param mode int which dicates the mode
   */
  private void errorChecks(String[] args, int mode) {
    if (args.length != 2 && args.length != 4) {
      System.err.println("ERROR: invalid number of arguments");
    } else if (!isNumeric(args[0])) {
      System.err.println("ERROR: invalid argument type");
    } else if (mode > 3 || mode < 1) {
      System.err.println("ERROR: invalid mode");
    } else if (validAlph(args, mode)) {
      System.err.println("ERROR: invalid alphabet symbol");
    }

  }

  /**
   * Helper method for errorChecks method that
   * checks that the characters used in
   * the arguements and
   * file for mode 1 are indeed valid.
   *
   * @param args String []
   * @param mode int which dictates mode
   * @return boolean
   */
  private boolean validAlph(String[] args, int mode) {
    String[] arr = {"A", "C", "G", "T"};
    boolean letter = false;
    boolean file = true;
    if (mode == 1) {
      char ch = ' ';
      for (int i = 0; i < text1.length(); i++) {
        ch = text1.charAt(i);
        if (Character.compare(ch, 'A') != 0 
        || Character.compare(ch, 'C') != 0
            || Character.compare(ch, 'G') != 0 
            || Character.compare(ch, 'T') != 0) {
          file = false;
          break;

        }

      }
    } else if (mode == 2) {
      for (int i = 0; i < 4; i++) {

        if (args[2].equals(arr[i])) {
          letter = true;
        }
      }

    } else if (mode == 3) {
      for (int i = 0; i < 4; i++) {

        if (args[1].equals(arr[i])) {
          letter = true;
        }
      }
    }

      return !(!letter || !file);
  
  }

  /**
   * Method used from StringUtil library:
   * https://commons.apache.org/proper/commons-lang/apidocs
   * /org/apache/commons/lang3/StringUtils.html
   * <p>
   * Method simply checks if the input is numeric
   *
   * @param cs is the CharSequence parsed that
   *           we want to check if it is numeric
   * @return boolean
   */
  public static boolean isNumeric(final CharSequence cs) {
    if ((cs + "").isEmpty()) {
      return false;
    }
    final int sz = cs.length();
    for (int i = 0; i < sz; i++) {
      if (!Character.isDigit(cs.charAt(i))) {
        return false;
      }
    }
    return true;
  }
}
