package osmo.tester.parser;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;

/**
 * @author Teemu Kanstren
 */
public class TestModel6 {
  @Transition("hello")
  public String transition1(String foo) {
    return "";
  }

  @Transition("world2")
  public void epix(String bar) {

  }

  @Guard("world")
  public String listCheck() {
    return "";
  }
}
