package osmo.tester.generator.strategy;

import osmo.tester.log.Logger;
import osmo.tester.generator.testlog.TestLog;

import java.util.Random;

/**
 * @author Teemu Kanstren
 */
public class ProbabilityStrategy implements ExitStrategy {
  private static Logger log = new Logger(ProbabilityStrategy.class);
  private final double threshold;
  private final Random random = new Random();

  public ProbabilityStrategy(double threshold) {
    this.threshold = threshold;
  }

  public boolean exitNow(TestLog testLog, boolean singleTest) {
    double v = random.nextDouble();
    log.debug("value "+v+" threshold "+threshold);
    return v >= threshold;
  }
}
