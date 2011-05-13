package osmo.tester;

import osmo.tester.generator.MainGenerator;
import osmo.tester.generator.algorithm.GenerationAlgorithm;
import osmo.tester.generator.algorithm.RandomAlgorithm;
import osmo.tester.generator.strategy.ExitStrategy;
import osmo.tester.generator.strategy.ProbabilityStrategy;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.parser.MainParser;

/**
 * @author Teemu Kanstren
 */
public class OSMOTester {
  private final Object modelObject;
  private ExitStrategy suiteStrategy = new ProbabilityStrategy(0.95d);
  private ExitStrategy testStrategy = new ProbabilityStrategy(0.9d);
  private GenerationAlgorithm algorithm = new RandomAlgorithm();

  public OSMOTester(Object modelObject) {
    this.modelObject = modelObject;
  }

  public void generate() {
    MainGenerator generator = new MainGenerator(algorithm, suiteStrategy, testStrategy);
    MainParser parser = new MainParser();
    FSM fsm = parser.parse(modelObject);
    generator.generate(fsm);
    System.out.println("generated " + fsm.getTestSuite().getHistory().size() + " tests.\n");
    System.out.println(fsm.getRequirements().printCoverage());
  }

  public void setSuiteStrategy(ExitStrategy suiteStrategy) {
    this.suiteStrategy = suiteStrategy;
  }

  public void setTestStrategy(ExitStrategy testStrategy) {
    this.testStrategy = testStrategy;
  }

  public void setAlgorithm(GenerationAlgorithm algorithm) {
    this.algorithm = algorithm;
  }

  public void setDebug(boolean debug) {
    Logger.debug = debug;
  }
}
