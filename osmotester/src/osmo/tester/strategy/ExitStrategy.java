package osmo.tester.strategy;

import osmo.tester.state.State;

/**
 * @author Teemu Kanstren
 */
public interface ExitStrategy {
  public boolean exitNow(State state);
}
