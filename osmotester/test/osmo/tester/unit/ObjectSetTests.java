package osmo.tester.unit;

import org.junit.Test;
import osmo.tester.model.dataflow.InputStrategy;
import osmo.tester.model.dataflow.ObjectSetInvariant;

import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class ObjectSetTests {
  @Test
  public void orderedTest() {
    ObjectSetInvariant<String> inv = new ObjectSetInvariant<String>(InputStrategy.ORDERED_LOOP);
    inv.addOption("one");
    inv.addOption("two");
    inv.addOption("three");
    assertEquals("one", inv.input());
    assertEquals("two", inv.input());
    assertEquals("three", inv.input());
    assertEquals("one", inv.input());
    assertEquals("two", inv.input());
    assertEquals("three", inv.input());
  }

  @Test
  public void randomizedTest() {
    ObjectSetInvariant<String> inv = new ObjectSetInvariant<String>();
    inv.addOption("one");
    inv.addOption("two");
    inv.addOption("three");
    String v1 = inv.input();
    String v2 = inv.input();
    String v3 = inv.input();
    assertTrue(v1.equals("one") || v1.equals("two") || v1.equals("three"));
    assertTrue(v2.equals("one") || v2.equals("two") || v2.equals("three"));
    assertTrue(v3.equals("one") || v3.equals("two") || v3.equals("three"));
    boolean fail = true;
    for (int i = 0 ; i < 10 ; i++) {
      String v1_2 = inv.input();
      String v2_2 = inv.input();
      String v3_2 = inv.input();
      if (!v1_2.equals(v1) || !v2_2.equals(v2) || !v3_2.equals(v3)) {
        fail = false;
        break;
      }
    }
    assertFalse("Random generation should be random, now it seems not to be.", fail);
  }

  @Test
  public void evaluationTest() {
    ObjectSetInvariant<String> inv = new ObjectSetInvariant<String>(InputStrategy.ORDERED_LOOP);
    inv.addOption("one");
    inv.addOption("two");
    inv.addOption("three");
    assertTrue("Should find \"one\" in the set of objects.", inv.evaluate("one"));
    assertTrue("Should find \"two\" in the set of objects.", inv.evaluate("two"));
    assertTrue("Should find \"three\" in the set of objects.", inv.evaluate("three"));
    assertFalse("Should not find \"four\" in the set of objects.", inv.evaluate("four"));
  }
}
