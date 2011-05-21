package osmo.tester.generation;

import osmo.tester.annotation.Before;
import osmo.tester.annotation.EndCondition;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Oracle;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.Transition;
import osmo.tester.model.Requirements;

import java.io.PrintStream;

/**
 * A test model with requirements that can all be covered.
 *
 * @author Teemu Kanstren
 */
public class TestModel5 {
  @RequirementsField
  private final Requirements req = new Requirements();
  public static final String REQ_HELLO = "hello";
  public static final String REQ_WORLD = "world";
  public static final String REQ_EPIX = "epix";
  private final PrintStream out;

  public TestModel5(PrintStream out) {
    this.out = out;
  }

  @Before
  public void reset() {
    req.clearCoverage();
  }

  @Guard("hello")
  public boolean helloCheck() {
    return !req.isCovered(REQ_HELLO) && !req.isCovered(REQ_WORLD) && !req.isCovered(REQ_EPIX);
  }

  @Transition("hello")
  public void transition1() {
    req.covered(REQ_HELLO);
    out.print(":hello");
  }

  @Guard("world")
  public boolean worldCheck() {
    return req.isCovered(REQ_HELLO) && !req.isCovered(REQ_WORLD) && !req.isCovered(REQ_EPIX);
  }

  @Transition("world")
  public void epix() {
    req.covered(REQ_WORLD);
    out.print(":world");
  }

  @Guard("epixx")
  public boolean kitted() {
    return req.isCovered(REQ_WORLD);
  }

  @Transition("epixx")
  public void epixx() {
    req.covered(REQ_EPIX);
    out.print(":epixx");
  }

  @Oracle("epixx")
  public void epixxO() {
    out.print(":epixx_oracle");
  }

  @Oracle
  public void stateCheck() {
    out.print(":gen_oracle");
  }

  @Oracle({"hello", "world"})
  public void sharedCheck() {
    out.print(":two_oracle");
  }

  @EndCondition
  public boolean end() {
    return req.getCovered().size() >= 2;
  }
}
