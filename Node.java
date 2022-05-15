
/**
 * This class is responsible for creating the
 * Node object which we use to build
 * the tree in Mode3
 */
public class Node {

  char letter = ' ';

  Node childA;
  Node childG;
  Node childC;
  Node childT;
  String word;
  // this is the sequence up untill the specified node
  int length;
  // this is the length of the sequence untill

  /**
   * Constructor method for Node object
   *
   * @param let The character which is associated with the node
   * @param w   The string built up untill the point of the node
   * @param len The int which tells us how long
   *            the word is at that point
   */
  public Node(char let, String w, int len) {
    word = w;
    letter = let;
    length = len;

  }

  /**
   * Returns the letter associated with the node
   *
   * @param n The node parsed
   * @return letter
   */
  public char getLetter(Node n) {

    return n.letter;
  }

}
