package osmo.tester.algorithm;

import osmo.tester.algorithm.GenerationAlgorithm;
import osmo.tester.model.FSMTransition;

import java.util.List;
import java.util.Random;

/**
 * @author Teemu Kanstren
 */
public class RandomAlgorithm implements GenerationAlgorithm {
  private Random random = new Random(1);

  public FSMTransition choose(List<FSMTransition> transitions) {
    int n = random.nextInt(transitions.size());
    return transitions.get(n);
  }
}
