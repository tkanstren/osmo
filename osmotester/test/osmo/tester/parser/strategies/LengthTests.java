package osmo.tester.parser.strategies;

import org.junit.Test;
import osmo.tester.OSMOTester;
import osmo.tester.examples.CalculatorModel;
import osmo.tester.generator.strategy.LengthStrategy;
import osmo.tester.generator.testsuite.TestCase;
import osmo.tester.generator.testsuite.TestSuite;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * 
 * @author Teemu Kanstren
 */
public class LengthTests {
  @Test
  public void length10() {
    testWithLength(10);
  }

  private void testWithLength(int expectedLength) {
    CalculatorModel calculator = new CalculatorModel();
    OSMOTester tester = new OSMOTester(calculator);
    LengthStrategy testStrategy = new LengthStrategy(expectedLength);
    tester.setTestStrategy(testStrategy);
    tester.setSuiteStrategy(testStrategy);
    tester.generate();
    TestSuite testLog = calculator.getHistory();
    //TODO: assert statements for wider set of constraints
    List<TestCase> history = testLog.getHistory();
    assertEquals("Number of tests generated", expectedLength, history.size());
    for (TestCase test : history) {
      assertEquals("Number of steps in a test case", expectedLength, test.getSteps().size());
    }
  }

  @Test
  public void length1() {
    testWithLength(1);
  }

  @Test
  public void length0() {
    testWithLength(0);
  }

  @Test
  public void negativeLength() {
    try {
      testWithLength(-1);
      fail("Negative length should throw exception.");
    } catch (Exception e) {
      assertEquals("Length cannot be < 0, was -1.", e.getMessage());
    }
  }
}
