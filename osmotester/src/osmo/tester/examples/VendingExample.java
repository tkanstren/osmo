package osmo.tester.examples;

import osmo.tester.OSMOTester;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestLogField;
import osmo.tester.annotation.Transition;
import osmo.tester.generator.testlog.TestLog;

/**
 * @author Teemu Kanstren
 */
public class VendingExample {
  private Scripter scripter = new Scripter();
  private int coins = 0;
  private int bottles = 10;
  @TestLogField
  private TestLog testLog = null;

  @Guard("all")
  public boolean gotBottles() {
    return bottles > 0;
  }

  @Before
  public void start() {
    coins = 0;
    //uncomment this for failure to continute with 0 available transitions
    bottles = 10;
    int tests = testLog.getHistory().size()+1;
    System.out.println("Starting test:"+ tests);
  }

  @AfterSuite
  public void done() {
    System.out.println("Created total of "+testLog.getHistory().size()+" tests.");
  }

  @Guard("20cents")
  public boolean allow20cents() {
    return coins <= 80;
  }

  @Transition("20cents")
  public void insert20cents() {
    scripter.step("INSERT 20\n");
    coins += 20;
  }

  @Guard("10cents")
  public boolean allow10cents() {
    return coins <= 90;
  }

  @Transition("10cents")
  public void insert10cents() {
    scripter.step("INSERT 10\n");
    coins += 10;
  }

  @Guard("50cents")
  public boolean allow50cents() {
    return coins <= 50;
  }

  @Transition("50cents")
  public void insert50cents() {
    scripter.step("INSERT 50\n");
    coins += 50;
  }

  @Guard("vend")
  public boolean allowVend() {
    return coins == 100;
  }

  @Transition("vend")
  public void vend() {
    scripter.step("VEND ("+bottles+")\n");
    coins = 0;
    bottles--;
  }

  public static void main(String[] args) {
    OSMOTester tester = new OSMOTester(new VendingExample());
//    tester.setDebug(true);
    tester.generate();
  }
}
