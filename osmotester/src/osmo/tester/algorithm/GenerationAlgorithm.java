package osmo.tester.algorithm;

import osmo.tester.model.FSMTransition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teemu Kanstren
 */
public interface GenerationAlgorithm {
  public FSMTransition choose(List<FSMTransition> transitions);
}
