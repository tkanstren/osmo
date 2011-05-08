package osmo.tester.generator.testlog;

import osmo.tester.model.FSMTransition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Teemu Kanstren
 */
public class TestStep {
  private final FSMTransition transition;
  private Collection<String> coveredRequirements = null;

  public TestStep(FSMTransition transition) {
    this.transition = transition;
  }

  public synchronized void covered(String requirement) {
    if (coveredRequirements == null) {
      coveredRequirements = new ArrayList<String>();
    }
    coveredRequirements.add(requirement);
  }

  public synchronized Collection<String> getCoveredRequirements() {
    if (coveredRequirements == null) {
      return Collections.EMPTY_LIST;
    }
    return coveredRequirements;
  }
}
