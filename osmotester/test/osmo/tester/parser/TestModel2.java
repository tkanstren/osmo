package osmo.tester.parser;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.TestSuiteField;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.Requirements;

/**
 * @author Teemu Kanstren
 */
public class TestModel2 {
  @RequirementsField
  private Requirements req1 = new Requirements();
  @RequirementsField
  private Requirements req2 = new Requirements();
  @TestSuiteField
  private TestSuite suite1 = null;
  @TestSuiteField
  private TestSuite suite2 = null;

  @Guard("foo")
  public boolean hello() {
    return false;
  }
}
