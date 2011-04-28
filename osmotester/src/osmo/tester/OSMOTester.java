package osmo.tester;

import osmo.tester.generator.RandomGenerator;
import osmo.tester.model.FSM;
import osmo.tester.parser.MainParser;

/**
 * @author Teemu Kanstren
 */
public class OSMOTester {
  private final Object modelObject;

  public OSMOTester(Object modelObject) {
    this.modelObject = modelObject;
  }

  public void generate(int testSize, int steps) {
    MainParser parser = new MainParser();
    FSM fsm = parser.parse(modelObject);
    RandomGenerator generator = new RandomGenerator();
    generator.generate(fsm, testSize, steps);
  }
}
