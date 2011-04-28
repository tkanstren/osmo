package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;
import osmo.tester.generator.RandomGenerator;
import osmo.tester.model.FSM;

/**
 * @author Teemu Kanstren
 */
public class OPModel {
  private int counter = 0;

  @Before
  public void start() {
    counter = 0;
    System.out.println("Starting new test case...");
  }

  @After
  public void end() {
    System.out.println("The End\n");
  }

  @Guard("start")
  public boolean startGuard() {
    return counter == 0;
  }

  @Transition("start")
  public void startState() {
    System.out.println("S:" + counter);
    counter++;
  }

  @Guard("decrease")
  public boolean decreaseGuard() {
    return counter > 0;
  }

  @Transition("decrease")
  public void DecreaseState() {
    counter--;
    System.out.println("- " + counter);
  }

  @Guard("increase")
  public boolean IncreaseGuard() {
    return counter > 0;
  }

  @Transition("increase")
  public void IncreaseState() {
    counter++;
    System.out.println("+ " + counter);
  }

  public static void main(String[] args) {
    MainParser parser = new MainParser();
    FSM fsm = parser.parse(OPModel.class);
    RandomGenerator generator = new RandomGenerator();
    generator.generate(fsm, 50);
  }
}
