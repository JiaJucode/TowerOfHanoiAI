package swappings;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Parser {
  static public final String EXCEPTION_MESSAGE = "size must be a number";

  static public List<Stack<Integer>> parse(String input) {
    List<String> rodStrings = List.of(input.split(" "));
    List<List<String>> rods = new ArrayList<>();
    for (String rodString : rodStrings) {
      if (rodString.length() >= 2) {
        if (
            rodString.charAt(0) != '('
                || rodString.charAt(rodString.length() - 1) != ')') {
          throw new IllegalArgumentException(EXCEPTION_MESSAGE);
        } else {
          String newString = rodString.substring(1, rodString.length() - 1);
          rods.add(List.of(newString.split(",")));
        }
      }
    }
    List<Stack<Integer>> parsedRods = new ArrayList<>();
    for (List<String> rod : rods) {
      Stack<Integer> currentRod = new Stack<>();
      for (String size : rod) {
        if (!size.isEmpty()) {
          try {
            currentRod.push(Integer.valueOf(size));
          } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(EXCEPTION_MESSAGE);
          }
        }
      }
      parsedRods.add(currentRod);
    }
    return parsedRods;
  }

}
