package osmo.tester.generator.strategy;

import osmo.tester.generator.testlog.TestLog;

/**
 * @author Teemu Kanstren
 */
public class LengthStrategy implements ExitStrategy {
  private final int length;

  public LengthStrategy(int length) {
    if (length < 0) {
      throw new IllegalArgumentException("Length cannot be < 0, was "+length+".");
    }
    this.length = length;
  }

  public boolean exitNow(TestLog testLog, boolean singleTest) {
    if (singleTest) {
      return testLog.currentSteps() >= length;
    }
    return testLog.getHistory().size() >= length;
  }
}
