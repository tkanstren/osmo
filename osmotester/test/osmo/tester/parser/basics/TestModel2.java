package osmo.tester.parser.basics;

import osmo.tester.annotation.Guard;

/**
 * @author Teemu Kanstren
 */
public class TestModel2 {
  @Guard("foo")
  public boolean hello() {
    return false;
  }
}
