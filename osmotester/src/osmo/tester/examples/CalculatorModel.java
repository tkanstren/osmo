package osmo.tester.examples;

import osmo.tester.OSMOTester;
import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.TestLogField;
import osmo.tester.annotation.Transition;
import osmo.tester.generator.strategy.LengthStrategy;
import osmo.tester.generator.testlog.TestLog;
import osmo.tester.model.Requirements;

/**
 * @author Teemu Kanstren
 */
public class CalculatorModel {
  @RequirementsField
  private Requirements requirement = new Requirements();
  @TestLogField
  private TestLog history = null;
  private int counter = 0;
  private int testCount = 1;

  public CalculatorModel() {
    requirement.add("increment");
    requirement.add("decrement");
  }

  public TestLog getHistory() {
    return history;
  }

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
    requirement.covered("decrease");
    counter--;
    System.out.println("- " + counter);
  }

  @Guard("increase")
  public boolean shallWeIncrease() {
    return counter > 0;
  }

  @Transition("increase")
  public void increaseState() {
    requirement.covered("increase");
    counter++;
    System.out.println("+ " + counter);
  }

  public static void main(String[] args) {
    OSMOTester tester = new OSMOTester(new CalculatorModel());
    tester.setSuiteStrategy(new LengthStrategy(100));
    tester.generate();
  }
}
