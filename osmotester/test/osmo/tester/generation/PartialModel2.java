package osmo.tester.generation;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Oracle;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.Transition;
import osmo.tester.model.Requirements;

import java.io.PrintStream;

/**
 * @author Teemu Kanstren
 */
public class PartialModel2 {
  @RequirementsField
  private final Requirements req;
  public static final String REQ_HELLO = "hello";
  public static final String REQ_WORLD = "world";
  public static final String REQ_EPIX = "epix";
  private final PrintStream out;

  public PartialModel2(Requirements req, PrintStream out) {
    this.req = req;
    this.out = out;
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

  @Transition("epixx")
  public void epixx() {
    req.covered(REQ_EPIX);
    out.print(":epixx");
  }

  @Oracle({"hello", "world"})
  public void sharedCheck() {
    out.print(":two_oracle");
  }
}
