package osmo.tester.state;

import osmo.tester.model.FSMTransition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teemu Kanstren
 */
public class TestCase {
  private List<FSMTransition> transitions = new ArrayList<FSMTransition>();

  public void addTransition(FSMTransition transition) {
    transitions.add(transition);
  }

  public List<FSMTransition> getTransitions() {
    return transitions;
  }
}
