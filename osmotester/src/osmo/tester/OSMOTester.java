package osmo.tester;

import osmo.tester.algorithm.GenerationAlgorithm;
import osmo.tester.algorithm.RandomAlgorithm;
import osmo.tester.generator.MainGenerator;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.parser.MainParser;
import osmo.tester.strategy.ExitStrategy;
import osmo.tester.strategy.ProbabilityStrategy;

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
    MainParser parser = new MainParser();
    FSM fsm = parser.parse(modelObject);
    MainGenerator generator = new MainGenerator(algorithm, suiteStrategy, testStrategy);
    generator.generate(fsm);
    System.out.println("total tests:"+generator.getState().getHistory().size());
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
