package osmo.tester.parser.basics;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;

/**
 * @author Teemu Kanstren
 */
public class TestModel3 {
  @Guard("foo")
  public String hello() {
    return "";
  }

  @Transition("foo")
  public void epixx() {
  }
}
