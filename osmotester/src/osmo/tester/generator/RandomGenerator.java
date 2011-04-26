package osmo.tester.generator;

import osmo.tester.model.FSM;

/**
 * @author Teemu Kanstren
 */
public class RandomGenerator {
  public void generate(FSM fsm, int steps) {
    //loop all transitions
    //check which have guards that report false, remove from list
    //pick one of the enabled by random
    //if none available, fail
    //add before, after
    //add state coverage analysis
  }
}
