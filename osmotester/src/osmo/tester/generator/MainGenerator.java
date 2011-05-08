package osmo.tester.generator;

import osmo.tester.generator.algorithm.GenerationAlgorithm;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;
import osmo.tester.generator.testlog.TestLog;
import osmo.tester.generator.strategy.ExitStrategy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Teemu Kanstren
 */
public class MainGenerator {
  private static Logger log = new Logger(MainGenerator.class);
  private TestLog testLog = null;
  private final GenerationAlgorithm algorithm;
  private final ExitStrategy suiteStrategy;
  private final ExitStrategy testStrategy;

  public MainGenerator(GenerationAlgorithm algorithm, ExitStrategy suiteStrategy, ExitStrategy testStrategy) {
    this.algorithm = algorithm;
    this.suiteStrategy = suiteStrategy;
    this.testStrategy = testStrategy;
  }

  public void generate(FSM fsm) {
    testLog = fsm.getTestLog();
    //TODO: check sanity of testSize, steps, etc.
    log.debug("Starting test suite generation");
    beforeSuite(fsm);
    while (!suiteStrategy.exitNow(testLog, false)) {
      log.debug("Starting new test generation");
      beforeTest(fsm);
      while (!testStrategy.exitNow(testLog, true)) {
        List<FSMTransition> enabled = getEnabled(fsm);
        //TODO: throw exception if no suitable one available + add tests
        FSMTransition next = algorithm.choose(enabled);
        log.debug("Taking transition "+next.getName());
        execute(fsm, next);
      }
      afterTest(fsm);
      log.debug("Finished new test generation");
    }
    afterSuite(fsm);
    log.debug("Finished test suite generation");
  }

  private void beforeSuite(FSM fsm) {
    Collection<Method> befores = fsm.getBeforeSuites();
    invokeAll(befores, "@BeforeSuite", fsm);
  }

  private void afterSuite(FSM fsm) {
    Collection<Method> afters = fsm.getAfterSuites();
    invokeAll(afters, "@AfterSuite", fsm);
  }

  private void beforeTest(FSM fsm) {
    testLog.startTest();
    Collection<Method> befores = fsm.getBefores();
    invokeAll(befores, "@Before", fsm);
  }

  private void afterTest(FSM fsm) {
    Collection<Method> afters = fsm.getAfters();
    invokeAll(afters, "@After", fsm);
    testLog.endTest();
  }

  private void invokeAll(Collection<Method> methods, String name, FSM fsm) {
    for (Method method : methods) {
      try {
        method.invoke(fsm.getModel());
      } catch (Exception e) {
        throw new RuntimeException("Error while calling "+name+" method ("+method.getName()+")", e);
      }
    }
  }

  private List<FSMTransition> getEnabled(FSM fsm) {
    Object obj = fsm.getModel();
    Collection<FSMTransition> allTransitions = fsm.getTransitions();
    List<FSMTransition> enabled = new ArrayList<FSMTransition>();
    enabled.addAll(allTransitions);
    for (FSMTransition transition : allTransitions) {
      for (Method guard : transition.getGuards()) {
        try {
          Boolean result = (Boolean)guard.invoke(obj);
          if (!result) {
            enabled.remove(transition);
          }
        } catch (Exception e) {
          throw new RuntimeException("Failed to invoke guard method.", e);
        }
      }
    }
    return enabled;
  }

  public void execute(FSM fsm, FSMTransition transition) {
    Method method = transition.getTransition();
    try {
      method.invoke(fsm.getModel());
    } catch (Exception e) {
      throw new RuntimeException("Exception while running transition ('"+transition.getName()+"'):", e);
    }
    testLog.add(transition);
  }

  public TestLog getTestLog() {
    return testLog;
  }
}
