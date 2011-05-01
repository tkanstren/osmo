package osmo.tester.strategy;

import osmo.tester.state.State;

/**
 * @author Teemu Kanstren
 */
public class LengthStrategy implements ExitStrategy {
  private final int length;

  public LengthStrategy(int length) {
    this.length = length;
  }

  public boolean exitNow(State state) {
    return state.totalSteps() > length;
  }
}
