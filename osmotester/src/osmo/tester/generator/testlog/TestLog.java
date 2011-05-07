package osmo.tester.generator.testlog;

import osmo.tester.model.FSMTransition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teemu Kanstren
 */
public class TestLog {
  private TestCase current = null;
  private List<TestCase> history = new ArrayList<TestCase>();

  public void startTest() {
    current = new TestCase();
  }

  public void endTest() {
    history.add(current);
  }

  public void add(FSMTransition transition) {
    current.addTransition(transition);
  }

  public int totalSteps() {
    int count = 0;
    for (TestCase test : history) {
      count += test.getTransitions().size();
    }
    count += current.getTransitions().size();
    return count;
  }

  public TestCase getCurrent() {
    return current;
  }

  public List<TestCase> getHistory() {
    return history;
  }
}
