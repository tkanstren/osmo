package osmo.tester.generator.algorithm;

import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.model.FSMTransition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static osmo.tester.TestUtils.oneOf;

/**
 * A test generation algorithm that is similar to the {@link RandomAlgorithm} but not preferring to take
 * a previously uncovered transitions (randomly) from the ones available. The difference is that a single
 * transition is not taken many times until other available ones have been taken.s
 *
 * @author Teemu Kanstren
 */
public class OptimizedRandomAlgorithm implements GenerationAlgorithm {
  @Override
  public FSMTransition choose(TestSuite history, List<FSMTransition> transitions) {
    Collection<FSMTransition> options = new ArrayList<FSMTransition>();
    for (FSMTransition transition : transitions) {
      if (!history.contains(transition)) {
        options.add(transition);
      }
    }
    if (options.size() == 0) {
      options = transitions;
    }
    return oneOf(options);
  }
}
