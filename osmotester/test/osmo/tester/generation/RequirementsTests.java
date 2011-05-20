package osmo.tester.generation;

import org.junit.Test;
import osmo.tester.OSMOTester;
import osmo.tester.model.Requirements;

import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class RequirementsTests {
  @Test
  public void fullCoverage() {
    fail("Not implemented");
  }

  @Test
  public void excessCoverage() {
    Requirements req = new Requirements();
    OSMOTester osmo = new OSMOTester(new TestModel2(req));
    osmo.setDebug(true);
    osmo.generate();
    assertEquals(3, req.getCovered().size());
    assertEquals(0, req.getRequirements().size());
    assertEquals(3, req.getExcess().size());
  }

  @Test
  public void lackingCoverage() {
    fail("Not implemented");
  }
}
