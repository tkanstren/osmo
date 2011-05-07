package osmo.tester.generator.strategy;

import osmo.tester.generator.testlog.TestLog;

/**
 * @author Teemu Kanstren
 */
public class LengthStrategy implements ExitStrategy {
  private final int length;

  public LengthStrategy(int length) {
    this.length = length;
  }

  public boolean exitNow(TestLog state) {
    return state.totalSteps() > length;
  }
}
