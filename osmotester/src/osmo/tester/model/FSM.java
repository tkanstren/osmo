package osmo.tester.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Teemu Kanstren
 */
public class FSM {
  private Map<String, FSMTransition> transitions = new HashMap<String, FSMTransition>();

  public FSMTransition createTransition(String name) {
    System.out.println("Creating transition:"+name);
    FSMTransition transition = transitions.get(name);
    if (transition != null) {
      return transition;
    }
    transition = new FSMTransition(name);
    transitions.put(name, transition);
    return transition;
  }

  public void check() {
    String errors = "";
    for (FSMTransition transition : transitions.values()) {
      if (transition.getTransition() == null) {
        errors += "Guard without transition:"+transition.getName()+"\n";
      }
    }
    if (errors.length() > 0) {
      throw new IllegalStateException("Invalid FSM:\n"+errors);
    }
  }

  public FSMTransition getTransition(String name) {
    return transitions.get(name);
  }
}
