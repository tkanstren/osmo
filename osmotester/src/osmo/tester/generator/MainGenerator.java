package osmo.tester.generator;

import osmo.tester.generator.algorithm.GenerationAlgorithm;
import osmo.tester.generator.strategy.ExitStrategy;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;
import osmo.tester.model.InvocationTarget;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The main test generator class.
 * Takes as input the finite state machine model parsed by {@link osmo.tester.parser.MainParser}.
 * Runs test generation on this model using the defined algorithms, exit strategies, etc.
 * 
 * @author Teemu Kanstren
 */
public class MainGenerator {
  private static Logger log = new Logger(MainGenerator.class);
  /** Test generation history. Initialized from the given model to enable sharing the object with model and generator. */
  private TestSuite suite = null;
  /** The set of enabled transitions in the current state is passed to this algorithm to pick one to execute. */
  private final GenerationAlgorithm algorithm;
  /** Defines when test suite generation should be stopped. Invoked between each test case. */
  private final ExitStrategy suiteStrategy;
  /** Defines when test case generation should be stopped. Invoked between each test step. */
  private final ExitStrategy testStrategy;

  /**
   * Constructor.
   *
   * @param algorithm The set of enabled transitions in the current state is passed to this algorithm to pick one to execute.
   * @param suiteStrategy Defines when test suite generation should be stopped. Invoked between each test case.
   * @param testStrategy Defines when test case generation should be stopped. Invoked between each test step.
   */
  public MainGenerator(GenerationAlgorithm algorithm, ExitStrategy suiteStrategy, ExitStrategy testStrategy) {
    this.algorithm = algorithm;
    this.suiteStrategy = suiteStrategy;
    this.testStrategy = testStrategy;
  }

  /**
   * Invoked to start the test generation using the configured parameters.
   *
   * @param fsm Describes the test model in an FSM format.
   */
  public void generate(FSM fsm) {
    suite = fsm.getTestSuite();
    log.debug("Starting test suite generation");
    beforeSuite(fsm);
    while (!suiteStrategy.exitNow(suite, true)) {
      log.debug("Starting new test generation");
      beforeTest(fsm);
      while (!testStrategy.exitNow(suite, false)) {
        List<FSMTransition> enabled = getEnabled(fsm);
        FSMTransition next = algorithm.choose(suite, enabled);
        log.debug("Taking transition "+next.getName());
        execute(next);
        if (checkEndConditions(fsm)) {
          //stop this test case generation if any end condition returns true
          break;
        }
      }
      afterTest(fsm);
      log.debug("Finished new test generation");
    }
    afterSuite(fsm);
    log.debug("Finished test suite generation");
  }

  /**
   * Calls every defind end condition and if any return true, also returns true. Otherwise, false.
   *
   * @param fsm The model object on which to invoke the methods.
   * @return true if current test case (not suite) generation should be stopped.
   */
  private boolean checkEndConditions(FSM fsm) {
    Collection<InvocationTarget> endConditions = fsm.getEndConditions();
    for (InvocationTarget ec : endConditions) {
      Boolean result = (Boolean)ec.invoke("@EndCondition");
      if (result) {
        return true;
      }
    }
    return false;
  }

  private void beforeSuite(FSM fsm) {
    Collection<InvocationTarget> befores = fsm.getBeforeSuites();
    invokeAll(befores, "@BeforeSuite");
  }

  private void afterSuite(FSM fsm) {
    Collection<InvocationTarget> afters = fsm.getAfterSuites();
    invokeAll(afters, "@AfterSuite");
  }

  private void beforeTest(FSM fsm) {
    //update history
    suite.startTest();
    Collection<InvocationTarget> befores = fsm.getBefores();
    invokeAll(befores, "@Before");
  }

  private void afterTest(FSM fsm) {
    Collection<InvocationTarget> afters = fsm.getAfters();
    invokeAll(afters, "@After");
    //update history
    suite.endTest();
  }

  /**
   * Invokes the given set of methods on the target test object.
   *
   * @param targets The methods to be invoked.
   */
  private void invokeAll(Collection<InvocationTarget> targets, String type) {
    for (InvocationTarget target : targets) {
      target.invoke(type);
    }
  }

  /**
   * Goes through all {@link osmo.tester.annotation.Transition} tagged methods in the given test model object,
   * invokes all associated {@link osmo.tester.annotation.Guard} tagged methods matching those transitions,
   * returning the set of {@link osmo.tester.annotation.Transition} methods that have no guards returning a value
   * of {@code false}.
   *
   * @param fsm Describes the test model.
   * @return The list of enabled {@link osmo.tester.annotation.Transition} methods.
   */
  private List<FSMTransition> getEnabled(FSM fsm) {
    Collection<FSMTransition> allTransitions = fsm.getTransitions();
    List<FSMTransition> enabled = new ArrayList<FSMTransition>();
    enabled.addAll(allTransitions);
    for (FSMTransition transition : allTransitions) {
      for (InvocationTarget guard : transition.getGuards()) {
        Boolean result = (Boolean)guard.invoke("@Guard");
        if (!result) {
          enabled.remove(transition);
        }
      }
    }
    if (enabled.size() == 0) {
      throw new IllegalStateException("No transition available.");
    }
    return enabled;
  }

  /**
   * Executes the given transition on the given model.
   *
   * @param transition  The transition to be executed.
   */
  public void execute(FSMTransition transition) {
    //we have to add this first or it will produce failures..
    suite.add(transition);
    InvocationTarget target = transition.getTransition();
    target.invoke("@Transition");
    invokeAll(transition.getOracles(), "@Oracle");
  }

  public TestSuite getSuite() {
    return suite;
  }
}
