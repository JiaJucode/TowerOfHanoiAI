import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import swappings.Swap;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestSwap {

  @Test
  public void TestBasic() {
    List<Pair<Integer, Integer>> process1 = new ArrayList<>();
    process1.add(new Pair(1, 2));
    process1.add(new Pair(1, 2));
    process1.add(new Pair(1, 2));
    process1.add(new Pair(0, 2));
    process1.add(new Pair(0, 2));
    process1.add(new Pair(0, 2));
    Swap test1 = new Swap("(1,2,3) (4,5,6) ()");
    assertEquals(process1, test1.getSteps(true));
    Swap test2 = new Swap("(1,5) (3,7) (4,2,8,6)");
    test2.getSteps(true);
    assertEquals("[8, 7, 6, 5, 4, 3, 2, 1]", test2.sortedRod());
  }


}
