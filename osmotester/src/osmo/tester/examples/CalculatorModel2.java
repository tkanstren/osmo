package osmo.tester.examples;

import osmo.tester.OSMOTester;
import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.strategy.LengthStrategy;

/**
 * This is the simple OSMO tester example model made by finite-state-machine (FSM) style
 *
 * This model is describing behavior of simple calculator.
 * The calculator can increase and decrease the count.
 *
 * @author Teemu Kanstren, Olli-Pekka Puolitaival
 */
public class CalculatorModel2 {
  private int counter = 0;
  private int testCount = 1;
  private State currentState;
  private enum State{
	  Start,
	  Decrease,
	  Increase,
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
    currentState = null;
  }

  @After
  public void end() {
    System.out.println("Test case ended\n");
  }

  @Guard("start")
  public boolean checkStart() {
    return currentState == null;
  }

  @Transition("start")
  public void startState() {
    System.out.println("S:" + counter);
    counter++;
    currentState = State.Start;
  }

  @Guard("decrease")
  public boolean DecreaseGuard() {
    return currentState == State.Increase || currentState == State.Decrease || currentState == State.Start;
  }

  @Transition("decrease")
  public void decreaseState() {
	System.out.println("Decreased: " + counter--);
    currentState = State.Decrease;
  }

  @Guard("increase")
  public boolean IncreaseGuard() {
	    return currentState == State.Increase || currentState == State.Decrease || currentState == State.Start;
  }

  @Transition("increase")
  public void increaseState() {
	System.out.println("Increased: " + counter++);
    currentState = State.Increase;
  }

  public static void main2(String[] args) {
    OSMOTester tester = new OSMOTester(new CalculatorModel2());
    tester.generate();
  }

  /**
   * Shows an example of configuring the generator.
   *
   * @param args not used.
   */
  public static void main(String[] args) {
    OSMOTester tester = new OSMOTester(new CalculatorModel2());
    tester.setAlgorithm(new RandomAlgorithm());
    tester.setDebug(true);
    tester.setSuiteStrategy(new LengthStrategy(10));
    tester.setTestStrategy(new LengthStrategy(10));
    tester.generate();
  }
}
