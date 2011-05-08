package osmo.tester.model;

import osmo.tester.generator.testlog.TestLog;
import osmo.tester.log.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Teemu Kanstren
 */
public class FSM {
  private static final Logger log = new Logger(FSM.class);
  private Map<String, FSMTransition> transitions = new HashMap<String, FSMTransition>();
  private Collection<Method> befores = new ArrayList<Method>();
  private Collection<Method> afters = new ArrayList<Method>();
  private Collection<Method> beforeSuites = new ArrayList<Method>();
  private Collection<Method> afterSuites = new ArrayList<Method>();
  private final TestLog testLog = new TestLog();
  private Requirements requirements;
  private final Object model;

  public FSM(Object model) {
    this.model = model;
  }

  public Object getModel() {
    return model;
  }

  public FSMTransition createTransition(String name) {
    log.debug("Creating transition: "+name);
    FSMTransition transition = transitions.get(name);
    if (transition != null) {
      return transition;
    }
    transition = new FSMTransition(name);
    transitions.put(name, transition);
    log.debug("Transition created");
    return transition;
  }

  public void check() {
    log.debug("Checking FSM validity");
    if (requirements == null) {
      log.debug("No requirements object defined. Creating new.");
      setRequirements(new Requirements());
    }
    String errors = "";
    for (FSMTransition transition : transitions.values()) {
      Method method = transition.getTransition();
      String name = transition.getName();
      log.debug("Checking transition:"+ name);
      if (method == null) {
        errors += "Guard without transition:"+ name +"\n";
        log.debug("Error: Found guard without a matching transition - "+ name);
      } else if (method.getParameterTypes().length > 0) {
        int p = method.getParameterTypes().length;
        errors += "Transition methods are not allowed to have parameters: \""+method.getName()+"()\" has "+p+" parameters.\n";
        log.debug("Error: Found transition with invalid parameters - "+ name);
      }
      errors = checkGuards(transition, errors);
    }
    if (errors.length() > 0) {
      throw new IllegalStateException("Invalid FSM:\n"+errors);
    }
    log.debug("FSM checked");
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

  public void addAfter(Method method) {
    afters.add(method);
  }

  public void addBefore(Method method) {
    befores.add(method);
  }

  public void addAfterSuite(Method method) {
    afterSuites.add(method);
  }

  public void addBeforeSuite(Method method) {
    beforeSuites.add(method);
  }

  public Collection<Method> getBefores() {
    return befores;
  }

  public Collection<Method> getAfters() {
    return afters;
  }

  public Collection<Method> getBeforeSuites() {
    return beforeSuites;
  }

  public Collection<Method> getAfterSuites() {
    return afterSuites;
  }

  public TestLog getTestLog() {
    return testLog;
  }

  public Requirements getRequirements() {
    return requirements;
  }

  public void setRequirements(Requirements requirements) {
    this.requirements = requirements;
    requirements.setTestLog(testLog);
  }
}
