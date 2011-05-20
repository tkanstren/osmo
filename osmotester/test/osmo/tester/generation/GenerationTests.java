package osmo.tester.generation;

import org.junit.Test;
import osmo.tester.OSMOTester;

import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class GenerationTests {
  @Test
  public void noEnabledTransition() {
    OSMOTester osmo = new OSMOTester(new TestModel1());
    osmo.generate();
    try {
      osmo.generate();
      fail("Generation without available transitions should fail.");
    } catch (IllegalStateException e) {
      assertEquals("No transition available.", e.getMessage());
    }
  }

}
