package osmo.tester;

import osmo.tester.generator.GenerationListener;
import osmo.tester.generator.MainGenerator;
import osmo.tester.generator.algorithm.GenerationAlgorithm;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.strategy.ExitStrategy;
import osmo.tester.generator.strategy.ProbabilityStrategy;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.parser.MainParser;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The main class for initiating the MBT tool.
 *
 * Create the model object using the annotations from osmo.tester.annotation package.
 * Set test generation stop strategies for both the overall test suite and individual test cases.
 * Set the algorithm for test generation.
 * Invoke generate().
 *
 * @author Teemu Kanstren
 */
public class OSMOTester {
  /** The test model itself, given by the user. */
  private final Object modelObject;
  /** When do we stop generating the overall test suite? (stopping all test generation)*/
  private ExitStrategy suiteStrategy = new ProbabilityStrategy(0.95d);
  /** When do we stop generating individual tests and start a new one? */
  private ExitStrategy testStrategy = new ProbabilityStrategy(0.9d);
  /** The algorithm to traverse the test model to generate test steps. */
  private GenerationAlgorithm algorithm = new RandomAlgorithm();
  /** Listeners to be notified about test generation events. */
  private Collection<GenerationListener> listeners = new ArrayList<GenerationListener>();

  /**
   * Create the tester with the initialized test model object.
   *
   * @param modelObject The model object defined using the OSMOTester annotations.
   */
  public OSMOTester(Object modelObject) {
    this.modelObject = modelObject;
  }

  /**
   * Invoke this to perform actual test generation from the given model, with the given algorithms and strategies.
   */
  public void generate() {
    MainGenerator generator = new MainGenerator(algorithm, suiteStrategy, testStrategy);
    MainParser parser = new MainParser();
    FSM fsm = parser.parse(modelObject);
    generator.generate(fsm);
    System.out.println("generated " + fsm.getTestSuite().getHistory().size() + " tests.\n");
    System.out.println(fsm.getRequirements().printCoverage());
  }

  /**
   * Set the strategy for stopping the generation of whole test suite.
   *
   * @param suiteStrategy The new strategy to stop overall suite generation.
   */
  public void setSuiteStrategy(ExitStrategy suiteStrategy) {
    this.suiteStrategy = suiteStrategy;
  }

  /**
   * Set the strategy for stopping the generation of individual test cases.
   *
   * @param testStrategy The new strategy to stop individual test generation.
   */
  public void setTestStrategy(ExitStrategy testStrategy) {
    this.testStrategy = testStrategy;
  }

  /**
   * Set the algorithm for test generation.
   *
   * @param algorithm New test generation algorithm.
   */
  public void setAlgorithm(GenerationAlgorithm algorithm) {
    this.algorithm = algorithm;
  }

  /**
   * If true, debug information is printed to console and file.
   *
   * @param debug True for debug information, false for no such information.
   */
  public void setDebug(boolean debug) {
    Logger.debug = debug;
  }

  public void addListener(GenerationListener listener) {
    listeners.add(listener);
  }
}
