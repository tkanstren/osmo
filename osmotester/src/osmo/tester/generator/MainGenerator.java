package osmo.tester.generator;

import osmo.tester.generator.algorithm.GenerationAlgorithm;
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
  private final TestLog testLog = new TestLog();
  private final GenerationAlgorithm algorithm;
  private final ExitStrategy suiteStrategy;
  private final ExitStrategy testStrategy;

  public MainGenerator(GenerationAlgorithm algorithm, ExitStrategy suiteStrategy, ExitStrategy testStrategy) {
    this.algorithm = algorithm;
    this.suiteStrategy = suiteStrategy;
    this.testStrategy = testStrategy;
  }

  public void generate(FSM fsm) {
    //TODO: check sanity of testSize, steps, etc.
    beforeSuite(fsm);
    beforeTest(fsm);
    while (true) {
      if (suiteStrategy.exitNow(testLog)) {
        break;
      }
      if (testStrategy.exitNow(testLog)) {
        afterTest(fsm);
        beforeTest(fsm);
      }
      List<FSMTransition> enabled = getEnabled(fsm);
      //TODO: throw exception if no suitable one available + add tests
      FSMTransition next = algorithm.choose(enabled);
      execute(fsm, next);
    }
    afterTest(fsm);
    afterSuite(fsm);
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
