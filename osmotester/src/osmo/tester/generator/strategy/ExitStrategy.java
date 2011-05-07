package osmo.tester.generator.strategy;

import osmo.tester.generator.testlog.TestLog;

/**
 * @author Teemu Kanstren
 */
public interface ExitStrategy {
  public boolean exitNow(TestLog state);
}
