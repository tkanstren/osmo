package osmo.tester.generation;

import org.junit.Test;
import osmo.tester.OSMOTester;
import osmo.tester.examples.CalculatorModel;
import osmo.tester.examples.CalculatorModel2;
import osmo.tester.examples.VendingExample;
import osmo.tester.generator.strategy.LengthStrategy;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.assertEquals;

/**
 * Test cases to verify the example models in osmo.tester.examples package.
 *
 * @author Teemu Kanstren
 */
public class ExampleTests {
  private static final String ln = System.getProperty("line.separator");

  @Test
  public void testCalculatorModel1() {
    ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    PrintStream ps = new PrintStream(out);
    OSMOTester osmo = new OSMOTester(new CalculatorModel(ps));
    LengthStrategy length3 = new LengthStrategy(3);
    LengthStrategy length2 = new LengthStrategy(2);
    osmo.setTestStrategy(length3);
    osmo.setSuiteStrategy(length2);
    osmo.generate();
    String expected = "first" + ln +
            "Starting new test case 1" + ln +
            "S:0" + ln +
            "+ 2" + ln +
            "+ 3" + ln +
            "Test case ended" + ln +
            "Starting new test case 2" + ln +
            "S:0" + ln +
            "+ 2" + ln +
            "+ 3" + ln +
            "Test case ended" + ln +
            "last"+ln;
    String actual = out.toString();
    assertEquals(expected, actual);
  }

  @Test
  public void testCalculatorModel2() {
    ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    PrintStream ps = new PrintStream(out);
    OSMOTester osmo = new OSMOTester(new CalculatorModel2(ps));
    LengthStrategy length3 = new LengthStrategy(3);
    LengthStrategy length2 = new LengthStrategy(2);
    osmo.setTestStrategy(length3);
    osmo.setSuiteStrategy(length2);
    osmo.generate();
    String expected = "first" + ln +
            "Starting new test case 1" + ln +
            "S:0" + ln +
            "Increased: 1" + ln +
            "Increased: 2" + ln +
            "Test case ended" + ln +
            "Starting new test case 2" + ln +
            "S:0" + ln +
            "Increased: 1" + ln +
            "Increased: 2" + ln +
            "Test case ended" + ln +
            "last" + ln;
    String actual = out.toString();
    assertEquals(expected, actual);
  }

  @Test
  public void testVendingExample() {
    ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    PrintStream ps = new PrintStream(out);
    OSMOTester osmo = new OSMOTester(new VendingExample(ps));
    LengthStrategy length3 = new LengthStrategy(3);
    LengthStrategy length2 = new LengthStrategy(2);
    osmo.setTestStrategy(length3);
    osmo.setSuiteStrategy(length2);
    osmo.generate();
    String expected = "Starting test:1" + ln +
            "INSERT 50" + ln +
            "CHECK(bottles == 10)" + ln +
            "CHECK(coins == 50)" + ln +
            "INSERT 10" + ln +
            "CHECK(bottles == 10)" + ln +
            "CHECK(coins == 60)" + ln +
            "INSERT 10" + ln +
            "CHECK(bottles == 10)" + ln +
            "CHECK(coins == 70)" + ln +
            "Starting test:2" + ln +
            "INSERT 50" + ln +
            "CHECK(bottles == 10)" + ln +
            "CHECK(coins == 50)" + ln +
            "INSERT 20" + ln +
            "CHECK(bottles == 10)" + ln +
            "CHECK(coins == 70)" + ln +
            "INSERT 10" + ln +
            "CHECK(bottles == 10)" + ln +
            "CHECK(coins == 80)" + ln +
            "Created total of 2 tests." + ln;
    String actual = out.toString();
    assertEquals(expected, actual);
  }
}
