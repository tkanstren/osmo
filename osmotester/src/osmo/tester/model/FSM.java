package osmo.tester.model;

import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.log.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the given model object in terms of a finite state machine (FSM).
 * Produced by the parser and used by the generator to create actual test cases.
 * 
 * @author Teemu Kanstren
 */
public class FSM {
  private static final Logger log = new Logger(FSM.class);
  /** Key = transition name (from @Transition("name")), Value = transition object */
  private Map<String, FSMTransition> transitions = new HashMap<String, FSMTransition>();
  /** List of generic guards that apply to all transitions. */
  private Collection<Method> genericGuards = new ArrayList<Method>();
  /** List of generic oracles that apply to all transitions. */
  private Collection<Method> genericOracles = new ArrayList<Method>();
  /** List of methods to be executed before each test case. */
  private Collection<Method> befores = new ArrayList<Method>();
  /** List of methods to be executed after each test case. */
  private Collection<Method> afters = new ArrayList<Method>();
  /** List of methods to be executed before the overall test suite. */
  private Collection<Method> beforeSuites = new ArrayList<Method>();
  /** List of methods to be executed after the overall test suite. */
  private Collection<Method> afterSuites = new ArrayList<Method>();
  /** The generated test suite (or one being generated). */
  private final TestSuite testSuite = new TestSuite();
  /** The list of requirements that needs to be covered. */
  private Requirements requirements;
  /** The model object itself, implementing the actual transition methods etc. */
  private final Object model;

  /**
   * Constructor.
   *
   * @param model The model object that implements the transitions, guards, etc.
   */
  public FSM(Object model) {
    this.model = model;
  }

  public Object getModel() {
    return model;
  }

  /**
   * Returns an existing object for the requested transition name or creates a new one if one was not previously
   * fount existing.
   *
   * @param name The name of the transition. Taken from @Transition("name")
   * @return A transition object for the requested name.
   */
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

  /**
   * Checks the FSM for validity. This includes the following constraints:
   * -Is a requirements object defined in the model? if so use it, otherwise create empty one.
   * -Check that each @Guard has a matching transition.
   * -Check that no @Transition method has parameters.
   * -Check that each @Guard returns a boolean value.
   * -Check that no @Guard method has a return value.
   */
  public void check(String errors) {
    log.debug("Checking FSM validity");
    if (requirements == null) {
      log.debug("No requirements object defined. Creating new.");
      //user the setRequirements method to also initialize the requirements object missing state
      setRequirements(new Requirements());
    }
    if (transitions.size() == 0) {
      errors += "No transitions found in given model object. Model cannot be processed.\n";
    }
    for (FSMTransition transition : transitions.values()) {
      Method method = transition.getTransition();
      String name = transition.getName();
      log.debug("Checking transition:"+ name);
      if (method == null) {
        errors += "Guard/Oracle without transition:"+ name +"\n";
        log.debug("Error: Found guard/oracle without a matching transition - "+ name);
      } else if (method.getParameterTypes().length > 0) {
        int p = method.getParameterTypes().length;
        errors += "Transition methods are not allowed to have parameters: \""+method.getName()+"()\" has "+p+" parameters.\n";
        log.debug("Error: Found transition with invalid parameters - "+ name);
      }
      errors = checkGuards(transition, errors);
      errors = checkOracles(transition, errors);
    }
    if (errors.length() > 0) {
      throw new IllegalStateException("Invalid FSM:\n"+errors);
    }
    log.debug("FSM checked");
  }

  /**
   * Check the guards for the given transition.
   * Since a new transition is previously generated for each guard that previously had no transition defined,
   * a guard without a transition is also checked here as it is simply a {@link FSMTransition} object without
   * the actual transition method defined but with a set of guards defined.
   *
   * @param transition The transition to check.
   * @param errors The current error message string.
   * @return The error msg string given with possible new errors appended.
   */
  private String checkGuards(FSMTransition transition, String errors) {
    //we add all generic guards to the set of guards for this transition. doing it here includes them in the checks
    for (Method guard : genericGuards) {
      transition.addGuard(guard);
    }
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

  /**
   * Checks all oracles to see that there are no oracles without associated transitions.
   * Also checks that the oracle methods have no parameters.
   *
   * @param transition The transition to check.
   * @param errors The current error message string.
   * @return The error msg string given with possible new errors appended.
   */
  private String checkOracles(FSMTransition transition, String errors) {
    for (Method oracle : genericOracles) {
      transition.addOracle(oracle);
    }
    for (Method oracle : transition.getOracles()) {
      Class<?>[] parameterTypes = oracle.getParameterTypes();
      if (parameterTypes.length > 0) {
        errors += "Oracle methods are not allowed to have parameters: \""+oracle.getName()+"()\" has "+parameterTypes.length+" parameters.\n";
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

  public TestSuite getTestSuite() {
    return testSuite;
  }

  public Requirements getRequirements() {
    return requirements;
  }

  /**
   * Sets the Requirements object, either from the parser if it found one in the model object or from this class
   * if not. Also initialized the requirements object to contain the {@link TestSuite} object for storing and
   * comparing covered requirements.
   *
   * @param requirements The requirements object for defining requirements that should be covered.
   */
  public void setRequirements(Requirements requirements) {
    this.requirements = requirements;
    requirements.setTestSuite(testSuite);
  }

  /**
   * Add a guard that should return true for all transitions in the test model.
   *
   * @param method The guard method to be invoked for evaluation.
   */
  public void addGenericGuard(Method method) {
    genericGuards.add(method);
  }

  /**
   * Add an oracle that should be evaluated for all transitions in the test model.
   *
   * @param method The oracle method to be invoked for evaluation.
   */
  public void addGenericOracle(Method method) {
    genericOracles.add(method);
  }
}
