package swappings;

import java.util.*;
import javafx.util.Pair;

public class Swap {
  static final String EXCEPTION_MESSAGE = "need at least 3 rods";
  private final List<Stack<Integer>> rods;
  private final List<Pair<Integer, Integer>> process = new ArrayList<>();
  private final int totalPieceCount;
  private int currentRodNumber;
  private boolean completeSteps = false;

  public Swap(String startingArrangement) {
    rods = Parser.parse(startingArrangement);
    if (rods.size() < 3) {
      throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
    int count = 0;
    for (Stack<Integer> rod : rods) {
      count += rod.size();
    }
    totalPieceCount = count;
  }

  //for testing
  public String sortedRod() {
    return rods.get(currentRodNumber).toString();
  }

  public List<Pair<Integer, Integer>> getSteps(boolean displaySteps) {
    int sorted = 0;
    currentRodNumber = getEmptyRod(displaySteps);
    System.out.println(rods);
    while (sorted != totalPieceCount) {
      moveSmaller();
      sorted++;
      if (displaySteps) {
        System.out.println(rods);
      }
    }
    completeSteps = true;
    return process;
  }

  private void addProcess(int nextRod, int currentRod) {
    rods.get(currentRod).push(rods.get(nextRod).pop());
    process.add(new Pair(nextRod, currentRod));
  }

  private void moveSmaller() {
    if (rods.get(currentRodNumber).isEmpty()) {
      Pair<Integer, Integer> largest = new Pair(0, 0);
      for (int rodNumber = 0; rodNumber != rods.size(); rodNumber++) {
        if (!rods.get(rodNumber).isEmpty()) {
          if (rods.get(rodNumber).peek() > largest.getValue()) {
            largest = new Pair(rodNumber, rods.get(rodNumber).peek());
          }
        }
      }
      addProcess(largest.getKey(), currentRodNumber);
    } else {
      PriorityQueue<Pair<Integer, Integer>> smaller =
          new PriorityQueue<>((n1, n2) -> n1.getValue() < n1.getValue() ? 1 : -1);
      PriorityQueue<Pair<Integer, Integer>> larger =
          new PriorityQueue<>((n1, n2) -> n1.getValue() < n1.getValue() ? 1 : -1);
      for (int rodNumber = 0; rodNumber != rods.size(); rodNumber++) {
        if (!rods.get(rodNumber).isEmpty()) {
          if (rods.get(rodNumber).peek() < rods.get(currentRodNumber).peek()) {
            smaller.add(new Pair(rodNumber, rods.get(rodNumber).peek()));
          } else {
            if (rodNumber != currentRodNumber) {
              larger.add(new Pair(rodNumber, rods.get(rodNumber).peek()));
            }
          }
        }
      }
      if (larger.isEmpty()) {
        addProcess(smaller.poll().getKey(), currentRodNumber);
      } else {
        int destination = larger.poll().getKey();
        int nextMovement = destination < rods.size() - 1 && destination + 1 != currentRodNumber
            ? destination + 1
            : destination - 1;
        int totalMovements = 0;
        while (!rods.get(currentRodNumber).isEmpty()
            && rods.get(destination).peek() > rods.get(currentRodNumber).peek()) {
          addProcess(currentRodNumber, nextMovement);
          totalMovements++;
        }
        addProcess(destination, currentRodNumber);
        while (totalMovements != 0) {
          addProcess(nextMovement, currentRodNumber);
          totalMovements--;
        }
      }
    }
  }

  private int getEmptyRod(boolean displayRod) {
    int smallestRod = 0;
    int smallestRodCount = rods.get(0).size();
    for (int rodNumber = 0; rodNumber != rods.size(); rodNumber++) {
      if (rods.get(rodNumber).isEmpty()) {
        return rodNumber;
      } else {
        if (rods.get(rodNumber).size() < smallestRodCount) {
          smallestRod = rodNumber;
          smallestRodCount = rods.get(rodNumber).size();
        }
      }
    }
    while (!rods.get(smallestRod).isEmpty()) {
      if (smallestRod == 0) {
        addProcess(smallestRod, smallestRod + 1);
      } else {
        addProcess(smallestRod, smallestRod - 1);
      }
    }
    return smallestRod;
  }
}
