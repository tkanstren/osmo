package osmo.tester.generation;

import org.junit.Test;
import osmo.tester.OSMOTester;
import osmo.tester.generator.GenerationListener;
import osmo.tester.generator.strategy.LengthStrategy;
import osmo.tester.model.Requirements;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static junit.framework.Assert.*;

/**
 * @author Teemu Kanstren
 */
public class GenerationTests {
  @Test
  public void noEnabledTransition() {
    OSMOTester osmo = new OSMOTester(new TestModel1());
    try {
      osmo.generate();
      fail("Generation without available transitions should fail.");
    } catch (IllegalStateException e) {
      assertEquals("No transition available.", e.getMessage());
    }
  }

  @Test
  public void generateTestModel2() {
    ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    PrintStream ps = new PrintStream(out);
    OSMOTester osmo = new OSMOTester(new TestModel2(new Requirements(), ps));
    LengthStrategy length3 = new LengthStrategy(3);
    LengthStrategy length1 = new LengthStrategy(1);
    osmo.setTestStrategy(length3);
    osmo.setSuiteStrategy(length1);
    osmo.generate();
    String expected = ":hello:world:epixx:epixx_oracle";
    String actual = out.toString();
    assertEquals(expected, actual);

  }

  @Test
  public void generateTestModel3() {
    ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    PrintStream ps = new PrintStream(out);
    OSMOTester osmo = new OSMOTester(new TestModel3(ps));
    LengthStrategy length3 = new LengthStrategy(3);
    LengthStrategy length1 = new LengthStrategy(1);
    osmo.setTestStrategy(length3);
    osmo.setSuiteStrategy(length1);
    osmo.generate();
    String expected = ":hello:gen_oracle:world:gen_oracle:epixx:epixx_oracle:gen_oracle";
    String actual = out.toString();
    assertEquals(expected, actual);
  }

  @Test
  public void generateTestModel3Times4() {
    ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    PrintStream ps = new PrintStream(out);
    OSMOTester osmo = new OSMOTester(new TestModel3(ps));
    LengthStrategy length3 = new LengthStrategy(3);
    LengthStrategy length4 = new LengthStrategy(4);
    osmo.setTestStrategy(length3);
    osmo.setSuiteStrategy(length4);
    osmo.generate();
    String one = ":hello:gen_oracle:world:gen_oracle:epixx:epixx_oracle:gen_oracle";
    String four = one;
    four += one;
    four += one;
    four += one;
    String actual = out.toString();
    assertEquals(four, actual);
  }

  @Test
  public void generateTestModel4Times2() {
    ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
    PrintStream ps = new PrintStream(out);
    OSMOTester osmo = new OSMOTester(new TestModel4(ps));
    LengthStrategy length3 = new LengthStrategy(3);
    LengthStrategy length2 = new LengthStrategy(2);
    osmo.setTestStrategy(length3);
    osmo.setSuiteStrategy(length2);
    osmo.generate();
    String one = ":hello:two_oracle:gen_oracle:world:two_oracle:gen_oracle:epixx:epixx_oracle:gen_oracle";
    String two = one;
    two += one;
    String actual = out.toString();
    assertEquals(two, actual);
  }
}
