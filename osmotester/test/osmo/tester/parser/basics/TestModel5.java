package osmo.tester.parser.basics;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;

/**
 * @author Teemu Kanstren
 */
public class TestModel5 {
  @Guard("foo")
  public boolean hello() {
    return false;
  }

  @Transition("foo")
  public void epixx(String bar) {
  }
}
