package osmo.tester.parser;

import org.junit.Before;
import org.junit.Test;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;
import osmo.tester.model.Requirements;

import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.*;

/**
 *
 *
 * @author Teemu Kanstren
 */
public class ParserTests {
  @Before
  public void setup() {
    Logger.debug = true;
  }

  @Test
  public void testModel1() throws Exception {
    MainParser parser = new MainParser();
    TestModel1 model = new TestModel1();
    FSM fsm = parser.parse(model);
    assertEquals("Number of @Before methods", 2, fsm.getBefores().size());
    assertEquals("Number of @BeforeSuite methods", 1, fsm.getBeforeSuites().size());
    assertEquals("Number of @After methods", 1, fsm.getAfters().size());
    assertEquals("Number of @AfterSuite methods", 1, fsm.getAfterSuites().size());
    //these also test for the correct number of guards
    assertTransitionPresent(fsm, "hello", 0, 2);
    assertTransitionPresent(fsm, "world", 3, 1);
    assertTransitionPresent(fsm, "epixx", 2, 3);
    assertEquals("Number of end conditions", 2, fsm.getEndConditions().size());
    assertNotNull("Should have TestLog set", model.getHistory());
    assertNotNull("Should have Requirements set", fsm.getRequirements());
  }


  @Test
  public void testModel2() {
    MainParser parser = new MainParser();
    try {
      FSM fsm = parser.parse(new TestModel2());
      fail("Should throw exception");
    } catch (Exception e) {
      String msg = e.getMessage();
      String expected = "Invalid FSM:\n" +
              "Only one @RequirementsField allowed in the model.\n" +
              "Guard/Oracle without transition:foo\n";
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
      String expected = "Invalid FSM:\n" +
              "@RequirementsField class must be of type "+ Requirements.class.getName()+". Was "+String.class.getName()+".\n"+
              "@TestSuiteField class must be of type osmo.tester.generator.testsuite.TestSuite. Was java.lang.String.\n"+
              "Invalid return type for guard (\"hello()\"):class java.lang.String.\n"+
              "Oracle methods are not allowed to have parameters: \"wrong()\" has 1 parameters.\n" +
              "Invalid return type for end condition (\"end()\"):void. Should be boolean.\n";
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
      //note that this exception checking will swallow real errors so it can be useful to print them..
//      e.printStackTrace();
      String msg = e.getMessage();
      String expected = "Invalid FSM:\n" +
              "@RequirementsField value was null, which is not allowed.\n"+
              "@TestSuiteField value was pre-initialized in the model, which is not allowed.\n"+
              "Guard methods are not allowed to have parameters: \"hello()\" has 1 parameters.\n"+
              "End condition methods are not allowed to have parameters: \"ending()\" has 1 parameters.\n";
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
      String expected = "Invalid FSM:\n" + "Transition methods are not allowed to have parameters: \"epixx()\" has 1 parameters.\n"+
              "Invalid return type for end condition (\"hello()\"):class java.lang.String. Should be boolean.\n" +
              "End condition methods are not allowed to have parameters: \"hello()\" has 1 parameters.\n";
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
              "Guard/Oracle without transition:world\n" +
              "Invalid return type for guard (\"listCheck()\"):class java.lang.String.\n";
      assertEquals(expected, msg);
    }
  }

  @Test
  public void testPartialModels() {
    MainParser parser = new MainParser();
    Requirements req = new Requirements();
    PartialModel1 model1 = new PartialModel1(req);
    PartialModel2 model2 = new PartialModel2(req);
    Collection<Object> models = new ArrayList<Object>();
    models.add(model1);
    models.add(model2);
    FSM fsm = parser.parse(models);
    assertEquals("Number of @Before methods", 2, fsm.getBefores().size());
    assertEquals("Number of @BeforeSuite methods", 1, fsm.getBeforeSuites().size());
    assertEquals("Number of @After methods", 2, fsm.getAfters().size());
    assertEquals("Number of @AfterSuite methods", 1, fsm.getAfterSuites().size());
    //these also test for the correct number of guards
    assertTransitionPresent(fsm, "hello", 0, 2);
    assertTransitionPresent(fsm, "world", 3, 1);
    assertTransitionPresent(fsm, "epixx", 2, 3);
    assertEquals("Number of end conditions", 2, fsm.getEndConditions().size());
    assertNotNull("Should have TestLog set", model1.getHistory());
    assertNotNull("Should have TestLog set", model2.getHistory());
    String s = "";
    String s1 = "";
    assertTrue("TestLog should be the same in partial models", model1.getHistory() == model2.getHistory());
    assertNotNull("Should have Requirements set", fsm.getRequirements());
  }

  @Test
  public void noMethods() {
    MainParser parser = new MainParser();
    try {
      FSM fsm = parser.parse(new Object());
      fsm.check("");
      fail("Should throw exception when no transition methods are available.");
    } catch (Exception e) {
      String msg = e.getMessage();
      String expected = "Invalid FSM:\n" +
              "No transitions found in given model object. Model cannot be processed.\n";
      assertEquals(expected, msg);
    }
  }

  private void assertTransitionPresent(FSM fsm, String name, int guardCount, int oracleCount) {
    FSMTransition transition = fsm.getTransition(name);
    assertNotNull("Transition '" + name + "' should be generated.", transition);
    assertNotNull("Transition '" + name + "' should have valid transition content.", transition.getTransition());
    assertEquals("Transition '" + name + "' should have " + guardCount + " guards.", guardCount, transition.getGuards().size());
    assertEquals("Transition '" + name + "' should have " + oracleCount + " oracles.", oracleCount, transition.getOracles().size());
  }
}
