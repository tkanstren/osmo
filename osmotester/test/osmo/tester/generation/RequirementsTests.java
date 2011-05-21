package osmo.tester.generation;

import org.junit.Test;
import osmo.tester.OSMOTester;
import osmo.tester.generator.strategy.LengthStrategy;
import osmo.tester.model.Requirements;

import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class RequirementsTests {
  @Test
  public void fullCoverage() {
    Requirements req = new Requirements();
    req.add(TestModel2.REQ_EPIX);
    req.add(TestModel2.REQ_HELLO);
    req.add(TestModel2.REQ_WORLD);
    OSMOTester osmo = new OSMOTester(new TestModel2(req));
    osmo.setTestStrategy(new LengthStrategy(3));
    osmo.generate();
    assertEquals(3, req.getCovered().size());
    assertEquals(3, req.getRequirements().size());
    assertEquals(0, req.getExcess().size());
  }

  @Test
  public void excessCoverage() {
    Requirements req = new Requirements();
    req.add(TestModel2.REQ_EPIX);
    OSMOTester osmo = new OSMOTester(new TestModel2(req));
    osmo.setTestStrategy(new LengthStrategy(3));
    osmo.generate();
    assertEquals(3, req.getCovered().size());
    assertEquals(1, req.getRequirements().size());
    assertEquals(2, req.getExcess().size());
  }

  @Test
  public void fullExcessCoverage() {
    Requirements req = new Requirements();
    OSMOTester osmo = new OSMOTester(new TestModel2(req));
    osmo.setTestStrategy(new LengthStrategy(3));
    osmo.generate();
    assertEquals(3, req.getCovered().size());
    assertEquals(0, req.getRequirements().size());
    assertEquals(3, req.getExcess().size());
  }

  @Test
  public void lackingCoverage() {
    Requirements req = new Requirements();
    req.add(TestModel2.REQ_EPIX);
    req.add(TestModel2.REQ_HELLO);
    req.add(TestModel2.REQ_WORLD);
    req.add("undefined");
    OSMOTester osmo = new OSMOTester(new TestModel2(req));
    osmo.setTestStrategy(new LengthStrategy(3));
    osmo.generate();
    assertEquals(3, req.getCovered().size());
    assertEquals(4, req.getRequirements().size());
    assertEquals(0, req.getExcess().size());
  }
}
