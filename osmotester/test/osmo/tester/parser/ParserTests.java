package osmo.tester.parser;

import org.junit.Test;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class ParserTests {
  @Test
  public void testModel1() throws Exception {
    MainParser parser = new MainParser();
    FSM fsm = parser.parse(new TestModel1());
    assertEquals("Number of @Before methods", 2, fsm.getBefores().size());
    assertEquals("Number of @BeforeSuite methods", 1, fsm.getBeforeSuites().size());
    assertEquals("Number of @After methods", 1, fsm.getAfters().size());
    assertEquals("Number of @AfterSuite methods", 1, fsm.getAfterSuites().size());
    assertTransitionPresent(fsm, "hello", 0);
    assertTransitionPresent(fsm, "world", 2);
    assertTransitionPresent(fsm, "epixx", 1);
    Thread.sleep(1000);
  }


  @Test
  public void testModel2() {
    MainParser parser = new MainParser();
    try {
      FSM fsm = parser.parse(new TestModel2());
      fail("Should throw exception");
    } catch (Exception e) {
      String msg = e.getMessage();
      String expected = "Invalid FSM:\n" + "Guard without transition:foo\n";
      assertEquals(expected, msg);
    }
  }

  @Test
  public void testModel3() {
    MainParser parser = new MainParser();
    try {
      FSM fsm = parser.parse(new TestModel3());
      fail("Should throw exception");
    } catch (Exception e) {
      String msg = e.getMessage();
      String expected = "Invalid FSM:\n" + "Invalid return type for guard (\"hello()\"):class java.lang.String.\n";
      assertEquals(expected, msg);
    }
  }

  @Test
  public void testModel4() {
    MainParser parser = new MainParser();
    try {
      FSM fsm = parser.parse(new TestModel4());
      fail("Should throw exception");
    } catch (Exception e) {
      String msg = e.getMessage();
      String expected = "Invalid FSM:\n" + "Guard methods are not allowed to have parameters: \"hello()\" has 1 parameters.\n";
      assertEquals(expected, msg);
    }
  }

  @Test
  public void testModel5() {
    MainParser parser = new MainParser();
    try {
      FSM fsm = parser.parse(new TestModel5());
      fail("Should throw exception");
    } catch (Exception e) {
      String msg = e.getMessage();
      String expected = "Invalid FSM:\n" + "Transition methods are not allowed to have parameters: \"epixx()\" has 1 parameters.\n";
      assertEquals(expected, msg);
    }
  }

  @Test
  public void testModel6() {
    MainParser parser = new MainParser();
    try {
      FSM fsm = parser.parse(new TestModel6());
      fail("Should throw exception");
    } catch (Exception e) {
      String msg = e.getMessage();
      String expected = "Invalid FSM:\n" +
              "Transition methods are not allowed to have parameters: \"transition1()\" has 1 parameters.\n" +
              "Transition methods are not allowed to have parameters: \"epix()\" has 1 parameters.\n" +
              "Guard without transition:world\n" +
              "Invalid return type for guard (\"listCheck()\"):class java.lang.String.\n";
      assertEquals(expected, msg);
    }
  }

  private void assertTransitionPresent(FSM fsm, String name, int guardCount) {
    FSMTransition transition = fsm.getTransition(name);
    assertNotNull("Transition '" + name + "' should be generated.", transition);
    assertNotNull("Transition '" + name + "' should have valid transition content.", transition.getTransition());
    assertEquals("Transition '" + name + "' should have " + guardCount + " guards.", guardCount, transition.getGuards().size());
  }
}
