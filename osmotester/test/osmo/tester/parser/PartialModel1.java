package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.EndCondition;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Oracle;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.TestSuiteField;
import osmo.tester.annotation.Transition;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.Requirements;

/**
 * @author Teemu Kanstren
 */
public class PartialModel1 {
  @RequirementsField
  private final Requirements requirements;
  @TestSuiteField
  private TestSuite history = null;

  public PartialModel1(Requirements requirements) {
    this.requirements = requirements;
  }

  public TestSuite getHistory() {
    return history;
  }

  @BeforeSuite
  public void beforeAll() {

  }

  @Before
  public void start1() {

  }

  @After
  public void end1() {

  }

  @Guard("world")
  public boolean listCheck() {
    return false;
  }

  @Transition("epixx")
  public void epixx() {

  }

  @Guard("epixx")
  public boolean kitted() {
    return false;
  }

  @Guard({"epixx", "world"})
  public boolean gaagaa() {
    return false;
  }

  @Oracle("epixx")
  public void epixxOracle() {
  }

  @Oracle({"hello", "epixx"})
  public void commonOracle() {
  }

  @EndCondition
  public boolean ec1() {
    return false;
  }

}
