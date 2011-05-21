package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
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
public class PartialModel2 {
  @RequirementsField
  private final Requirements requirements;
  @TestSuiteField
  private TestSuite history = null;

  public PartialModel2(Requirements requirements) {
    this.requirements = requirements;
  }

  public TestSuite getHistory() {
    return history;
  }

  @AfterSuite
  public void endAll() {

  }

  @Before
  public void start2() {

  }

  @After
  public void end2() {

  }

  @Transition("hello")
  public void transition1() {

  }

  @Guard("world")
  public boolean listCheck2() {
    return false;
  }

  @Transition("world")
  public void epix() {

  }

  @Oracle
  public void stateOracle() {
  }

  @EndCondition
  public boolean ec2() {
    return false;
  }
}
