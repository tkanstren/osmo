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
  public void testModel1() {
    MainParser parser = new MainParser();
    FSM fsm = parser.parse(TestModel1.class);
    assertTransitionPresent(fsm, "hello", 0);
    assertTransitionPresent(fsm, "world", 2);
    assertTransitionPresent(fsm, "epixx", 1);
  }

  @Test
  public void testModel2() {
    MainParser parser = new MainParser();
    try {
      FSM fsm = parser.parse(TestModel2.class);
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
      FSM fsm = parser.parse(TestModel3.class);
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
      FSM fsm = parser.parse(TestModel4.class);
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
      FSM fsm = parser.parse(TestModel5.class);
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
      FSM fsm = parser.parse(TestModel6.class);
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
