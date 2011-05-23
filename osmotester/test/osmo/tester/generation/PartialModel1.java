package osmo.tester.generation;

import osmo.tester.annotation.Before;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Oracle;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.Transition;
import osmo.tester.model.Requirements;

import java.io.PrintStream;

/**
 * @author Teemu Kanstren
 */
public class PartialModel1 {
  @RequirementsField
  private final Requirements req;
  public static final String REQ_HELLO = "hello";
  public static final String REQ_WORLD = "world";
  public static final String REQ_EPIX = "epix";
  private final PrintStream out;

  public PartialModel1(Requirements req, PrintStream out) {
    this.req = req;
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

  @Guard("epixx")
  public boolean kitted() {
    return req.isCovered(REQ_WORLD);
  }

  @Oracle("epixx")
  public void epixxO() {
    out.print(":epixx_oracle");
  }

  @Oracle
  public void stateCheck() {
    out.print(":gen_oracle");
  }
}
