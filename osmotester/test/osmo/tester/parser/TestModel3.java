package osmo.tester.parser;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.TestSuiteField;
import osmo.tester.annotation.Transition;

/**
 * @author Teemu Kanstren
 */
public class TestModel3 {
  @RequirementsField
  private String requirements = null;
  @TestSuiteField
  private String suite = null;

  @Guard("foo")
  public String hello() {
    return "";
  }

  @Transition("foo")
  public void epixx() {
  }
}