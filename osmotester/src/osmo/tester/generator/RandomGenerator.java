package osmo.tester.generator;

import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * @author Teemu Kanstren
 */
public class RandomGenerator {
  private Random random = new Random(1);

  public void generate(FSM fsm, int steps) {
    for (int i = 0 ; i < steps ; i++) {
      List<FSMTransition> enabled = getEnabled(fsm);
      //TODO: throw exception if no suitable none available
      FSMTransition next = pickOne(enabled);
      execute(fsm, next);
    }
    //if none available, fail
    //add before, after
    //add state coverage analysis
  }

  private FSMTransition pickOne(List<FSMTransition> transitions) {
    int n = random.nextInt(transitions.size());
    return transitions.get(n);
  }

  public void execute(FSM fsm, FSMTransition transition) {
    Method method = transition.getTransition();
    try {
      method.invoke(fsm.getModel());
    } catch (Exception e) {
      throw new RuntimeException("Exception while running transition ('"+transition.getName()+"'):", e);
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

  public static void main(String[] args) {
    Collection<String> test = new ArrayList<String>();
    test.add("1");
    test.add("2");
    test.add("3");
    test.add("4");
    test.add("5");
    Random random = new Random();
    for (int i = 0 ; i < 100 ; i++) {
      int n = random.nextInt(test.size());
      System.out.println(n);
    }
  }
}
