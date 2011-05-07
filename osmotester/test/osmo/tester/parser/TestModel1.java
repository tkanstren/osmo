package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.TestLogField;
import osmo.tester.annotation.Transition;
import osmo.tester.generator.testlog.TestLog;

/**
 * @author Teemu Kanstren
 */
public class TestModel1 {
  @TestLogField
  private TestLog history = null;

  public TestLog getHistory() {
    return history;
  }

  @Before
  public void start1() {

  }

  @Before
  public void start2() {

  }

  @After
  public void end() {

  }

  @BeforeSuite
  public void beforeAll() {

  }

  @AfterSuite
  public void endAll() {

  }

  @Transition("hello")
  public void transition1() {

  }

  @Transition("world")
  public void epix() {

  }

  @Guard("world")
  public boolean listCheck() {
    return false;
  }

  @Guard("world")
  public boolean listCheck2() {
    return false;
  }

  @Transition("epixx")
  public void epixx() {

  }

  @Guard("epixx")
  public boolean kitted() {
    return false;
  }
}
