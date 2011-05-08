package osmo.tester.generator.testlog;

import osmo.tester.model.FSMTransition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teemu Kanstren
 */
public class TestCase {
  private List<TestStep> steps = new ArrayList<TestStep>();
  private TestStep currentStep = null;

  public void addTransition(FSMTransition transition) {
    TestStep step = new TestStep(transition);
    steps.add(step);
    currentStep = step;
  }

  public void covered(String requirement) {
    currentStep.covered(requirement);
  }

  public List<TestStep> getSteps() {
    return steps;
  }
}
