
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

/**
 * This is the class which is responsible for the implemenation of mode 3
 */
public class Mode3 {

  int alphSize = 0;
  char startLetter = ' ';
  double seconds = 0;
  StopWatch watch = new StopWatch();

  Node ultimate;
  // used to keep track of longest string & length

  char[] alph;
  Stack<Character> stack = new Stack<Character>();
  // this stack is used in the identification of repeats.

  /**
   * Constructor method for mode3 object
   *
   * @param alph   int that dictates the alphabet size
   * @param letter char that dictates the starting letter
   * @param sec    double that dictates time allowed
   * @throws IOException to catch error
   */
  public Mode3(int alph, char letter, double sec) throws IOException {

    alphSize = alph;
    startLetter = letter;
    seconds = sec;
    Node root = new Node(letter, letter + "", 1);
    ultimate = root;
    this.alph = generateAlphabet();
    StopWatch watch = new StopWatch();
    buildTrie(root);
    createOutFile();
  }

  /**
   * This is a recursive implementation of a trie.
   * The recursion makes it easy to
   * read and simple to
   * understand. We build the trie using 4 "sub"
   * methods which are called using a
   * switch statement
   * in this method.
   *
   * @param pointer this is the node parsed to the buildTrie method
   */
  public void buildTrie(Node pointer) {

    if (alphSize == 2 && ultimate.length == 8) {
      return;
    }

    if (alphSize == 3 && ultimate.length == 30) {
      return;

    }

    if (watch.elapsedTime() >= seconds - 0.02) {
      return;
    }

    for (int i = 0; i < alphSize; i++) {

      switch (alph[i]) {

        case 'A':
          buildA(pointer);
          break;
        case 'C':
          buildC(pointer);
          break;
        case 'G':
          buildG(pointer);
          break;
        case 'T':
          buildT(pointer);
          break;
        default:
          // literally to shut the style checker up
      }
    }

  }

  /**
   * This is a helper method which plays a role in
   * the recursive implementation of
   * the trie
   * construction
   * 
   * @param pointer node
   */
  private void buildC(Node pointer) {

    if (!checkIfRepetitionBetter(pointer.word + "C")) {
      pointer.childC = new Node('C', pointer.word + "C",
          pointer.length + 1);
      if (pointer.childC.length > ultimate.length) {
        ultimate = pointer.childC;
      }

      buildTrie(pointer.childC);

    }

  }

  /**
   * This is a helper method which plays a role in
   * the recursive implementation of
   * the trie
   * construction
   *
   * @param pointer node
   */
  private void buildG(Node pointer) {

    if (!checkIfRepetitionBetter(pointer.word + "G")) {
      pointer.childG = new Node('G', pointer.word
          + "G", pointer.length + 1);

      if (pointer.childG.length > ultimate.length) {
        ultimate = pointer.childG;
      }
      buildTrie(pointer.childG);

    }
  }

  /**
   * This is a helper method which plays a role in the
   * recursive implementation of
   * the trie
   * construction
   *
   * @param pointer node
   */
  private void buildT(Node pointer) {

    if (!checkIfRepetitionBetter(pointer.word + "T")) {
      pointer.childT = new Node('T', pointer.word
          + "T", pointer.length + 1);

      if (pointer.childT.length > ultimate.length) {
        ultimate = pointer.childT;
      }
      buildTrie(pointer.childT);

    }
  }

  /**
   * This is a helper method which plays a role
   * in the recursive implementation of
   * the trie
   * construction
   *
   * @param pointer node
   */
  private void buildA(Node pointer) {

    if (!checkIfRepetitionBetter(pointer.word + "A")) {
      pointer.childA = new Node('A', pointer.word
          + "A", pointer.length + 1);
      if (pointer.childA.length > ultimate.length) {
        ultimate = pointer.childA;
      }
      buildTrie(pointer.childA);

    }
  }

  /**
   * This method checks if the sequence contains a
   * repetition caused by the latest
   * edition of a
   * character. Returns true if there is a repetition
   * and false if there is not a
   * repetition.
   *
   * 
   * @param word String which we check if there are repetitions in
   * @return boolean
   */
  private boolean checkIfRepetitionBetter(String word) {

    char fin = word.charAt(word.length() - 1);

    int pos = word.length() - 2;

    stack.push(fin);
    for (int i = word.length() - 2; fin != word.charAt(i) && i > 0; i--) {
      // runs from end of string untill an xyx sequence is found
      stack.push(word.charAt(i));

      pos--;

    }
    stack.push(fin);

    String stackHelp = "";
    for (int j = 0; j < stack.size();) {
      stackHelp += stack.pop();
    }

    int check = word.indexOf(stackHelp);

    return !(check == pos || check == -1);

  }

  /**
   * This method generates the alphabet to be used
   * based upon the alphabet size
   *
   * @return char [] real : contains alphabet that will be used
   */
  private char[] generateAlphabet() {

    char[] alph1 = { 'A', 'C', 'G', 'T' };
    char[] real = new char[alphSize];

    // remove startChar from alph, so that when
    // we copy elements to real[] we dont
    // have repetition of the starting letter
    for (int k = 0; k < 4; k++) {
      if (Character.compare(alph1[k], startLetter) == 0) {
        alph1 = shiftLeft(k, alph1);
      }
    }

    real[0] = startLetter;

    for (int i = 0; i < alphSize - 1; i++) {
      real[i + 1] = alph1[i];
    }

    return real;
  }

  /**
   * Simple shift left of an array given a specific element of the array
   *
   * @param k Element from where the array will be shifted left
   * @param c The char array that the shift operation
   *          will be performed on
   * @return char [] real.
   */
  private char[] shiftLeft(int k, char[] c) {

    for (int i = k; i < c.length - 1; i++) {
      c[i] = c[i + 1];
    }
    return c;
  }

  /**
   * This method is responsible for creating the
   * output file with the correct
   * name. The PrintWriter
   * is used to write to the file
   *
   * 
   * @throws IOException To catch error in file creation
   */
  private void createOutFile() throws IOException {

    // code used to ensure the file is named correctly
    File out = new File("../out");
    File[] allF = out.listFiles();

    int numFiles = allF.length;
    int outFiles = 1;
    for (int i = 0; i < numFiles; i++) {
      String fileName = allF[i].getName();

      if (allF[i].isFile() && fileName.startsWith("out")
          && fileName.endsWith("txt")) {
        outFiles++;
      }
    }
    String fileName1 = "../out/out" + outFiles + "_opt.txt";

    // code that creates the file
    File file = new File(fileName1);

    // segment of code that writes to the newly created file

    PrintWriter pr = new PrintWriter(file);
    pr.print(ultimate.length + " - " + ultimate.word);
    pr.close();

  }
}
