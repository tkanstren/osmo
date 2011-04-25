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
    FSM fsm = parser.parse(TestModel.class);
    assertTransitionPresent(fsm, "hello", 0);
    assertTransitionPresent(fsm, "world", 1);
  }

  private void assertTransitionPresent(FSM fsm, String name, int guardCount) {
    FSMTransition transition = fsm.getTransition(name);
    assertNotNull("Transition '"+name+"' should be generated.", transition);
    assertNotNull("Transition '"+name+"' should have valid transition content.", transition.getTransition());
    assertEquals("Transition '"+name+"' should have "+guardCount+" guards.", guardCount, transition.getGuards().size());
  }
}
