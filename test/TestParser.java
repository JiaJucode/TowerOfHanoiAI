import org.junit.jupiter.api.Test;
import swappings.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class TestParser {
  @Test
  public void testEmpty() {
    List<Stack<Integer>> empty = new ArrayList<>();
    assertEquals(empty, Parser.parse(""));
  }

  @Test
  public void InvalidInput() {
    IllegalArgumentException thrown1 = assertThrows(
        IllegalArgumentException.class,
        () -> Parser.parse("() (a,b)")
    );
    assertTrue(thrown1.getMessage().contains(Parser.EXCEPTION_MESSAGE));

    IllegalArgumentException thrown2 = assertThrows(
        IllegalArgumentException.class,
        () -> Parser.parse("(  ,) (a,b)")
    );
    assertTrue(thrown2.getMessage().contains(Parser.EXCEPTION_MESSAGE));
  }

  @Test
  public void validInput() {
    List<Stack<Integer>> list1 = new ArrayList<>();
    Stack<Integer> rod1 = new Stack<>();
    rod1.push(1);
    rod1.push(2);
    rod1.push(3);
    Stack<Integer> rod2 = new Stack<>();
    rod2.push(4);
    rod2.push(5);
    rod2.push(9);
    list1.add(rod1);
    list1.add(rod2);
    List<Stack<Integer>> list2 = new ArrayList<>();
    list2.add(new Stack<>());
    list2.add(rod1);
    assertEquals(list1, Parser.parse("(1,2,3) (4,5,9)"));
    assertEquals(list2, Parser.parse("() (1,2,3)"));
  }
}
