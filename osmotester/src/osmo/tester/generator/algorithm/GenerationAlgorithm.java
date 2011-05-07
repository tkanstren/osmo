package osmo.tester.generator.algorithm;

import osmo.tester.model.FSMTransition;

import java.util.List;

/**
 * @author Teemu Kanstren
 */
public interface GenerationAlgorithm {
  public FSMTransition choose(List<FSMTransition> transitions);
}
