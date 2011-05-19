package osmo.tester.basics;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;

/**
 * @author Teemu Kanstren
 */
public class TestModel4 {
  @Guard("foo")
  public boolean hello(String foo) {
    return false;
  }

  @Transition("foo")
  public void epixx() {
  }
}
