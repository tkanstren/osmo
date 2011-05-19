package osmo.tester.parser;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.TestSuiteField;
import osmo.tester.annotation.Transition;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.Requirements;

/**
 * @author Teemu Kanstren
 */
public class TestModel4 {
  @RequirementsField
  private Requirements requirements = null;
  @TestSuiteField
  private TestSuite suite = new TestSuite();

  @Guard("foo")
  public boolean hello(String foo) {
    return false;
  }

  @Transition("foo")
  public void epixx() {
  }
}
