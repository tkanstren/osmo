package osmo.tester.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Teemu Kanstren
 */
public class FSM {
  private Map<String, FSMTransition> transitions = new HashMap<String, FSMTransition>();
  private final Object model;

  public FSM(Object model) {
    this.model = model;
  }

  public Object getModel() {
    return model;
  }

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
      Method method = transition.getTransition();
      if (method == null) {
        errors += "Guard without transition:"+transition.getName()+"\n";
      } else if (method.getParameterTypes().length > 0) {
        int p = method.getParameterTypes().length;
        errors += "Transition methods are not allowed to have parameters: \""+method.getName()+"()\" has "+p+" parameters.\n";
      }
      errors = checkGuards(transition, errors);
    }
    if (errors.length() > 0) {
      throw new IllegalStateException("Invalid FSM:\n"+errors);
    }
  }

  private String checkGuards(FSMTransition transition, String errors) {
    for (Method guard : transition.getGuards()) {
      Class<?> type = guard.getReturnType();
      if (!(type.equals(boolean.class))) {
        errors += "Invalid return type for guard (\""+guard.getName()+"()\"):"+type+".\n";
      }
      Class<?>[] parameterTypes = guard.getParameterTypes();
      if (parameterTypes.length > 0) {
        errors += "Guard methods are not allowed to have parameters: \""+guard.getName()+"()\" has "+parameterTypes.length+" parameters.\n";
      }
    }
    return errors;
  }

  public FSMTransition getTransition(String name) {
    return transitions.get(name);
  }

  public Collection<FSMTransition> getTransitions() {
    return transitions.values();
  }
}
