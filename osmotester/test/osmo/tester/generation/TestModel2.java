package osmo.tester.generation;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.Transition;
import osmo.tester.model.Requirements;

/**
 * A test model with requirements that can all be covered.
 *
 * @author Teemu Kanstren
 */
public class TestModel2 {
  @RequirementsField
  private final Requirements req;
  public static final String REQ_HELLO = "hello";
  public static final String REQ_WORLD = "world";
  public static final String REQ_EPIX = "epix";

  public TestModel2(Requirements req) {
    this.req = req;
  }

  @Guard("hello")
  public boolean helloCheck() {
    return !req.isCovered(REQ_HELLO) && !req.isCovered(REQ_WORLD) && !req.isCovered(REQ_EPIX);
  }

  @Transition("hello")
  public void transition1() {
    req.covered(REQ_HELLO);
  }

  @Guard("world")
  public boolean worldCheck() {
    return req.isCovered(REQ_HELLO) && !req.isCovered(REQ_WORLD) && !req.isCovered(REQ_EPIX);
  }

  @Transition("world")
  public void epix() {
    req.covered(REQ_WORLD);
  }

  @Guard("epixx")
  public boolean kitted() {
    return req.isCovered(REQ_WORLD);
  }

  @Transition("epixx")
  public void epixx() {
    req.covered(REQ_EPIX);
  }
}
