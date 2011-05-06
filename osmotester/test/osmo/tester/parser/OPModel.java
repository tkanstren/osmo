package osmo.tester.parser;

import osmo.tester.OSMOTester;
import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;

/**
 * @author Teemu Kanstren
 */
public class OPModel {
  //@Requirements
  //@TestLog
  private int counter = 0;
  private int testCount = 1;

  @BeforeSuite
  public void first() {
    System.out.println("first");
  }

  @AfterSuite
  public void last() {
    System.out.println("last");
  }

  @Before
  public void start() {
    counter = 0;
    System.out.println("Starting new test case "+testCount);
    testCount++;
  }

  @After
  public void end() {
    System.out.println("Test case ended\n");
  }

  @Guard("start")
  public boolean checkStart() {
    return counter == 0;
  }

  @Transition("start")
  public void startState() {
    System.out.println("S:" + counter);
    counter++;
  }

  @Guard("decrease")
  public boolean toDecreaseOrNot() {
    return counter > 1;
  }

  @Transition("decrease")
  public void decreaseState() {
    counter--;
    System.out.println("- " + counter);
  }

  @Guard("increase")
  public boolean shallWeIncrease() {
    return counter > 0;
  }

  @Transition("increase")
  public void increaseState() {
    counter++;
    System.out.println("+ " + counter);
  }

  public static void main(String[] args) {
    OSMOTester tester = new OSMOTester(new OPModel());
    //generate 50 steps, where 10 steps form a test case
    tester.generate();
  }
}
