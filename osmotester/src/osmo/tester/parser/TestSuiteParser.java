package osmo.tester.parser;

import osmo.tester.annotation.TestSuiteField;
import osmo.tester.generator.testsuite.TestSuite;
import osmo.tester.log.Logger;

import java.lang.reflect.Field;

/**
 * Parses {@link TestSuiteField} annotations from the given model object.
 * 
 * @author Teemu Kanstren
 */
public class TestSuiteParser implements AnnotationParser {
  private static Logger log = new Logger(TestSuiteParser.class);

  @Override
  public void parse(ParserParameters parameters) {
    TestSuiteField annotation = (TestSuiteField) parameters.getAnnotation();
    log.debug("TestLogField processing");
    try {
      Field field = parameters.getField();
      //we bypass the private etc. modifiers to access it
      field.setAccessible(true);
      Object model = parameters.getModel();
      //TODO: add contract: testsuite cannot be null
      TestSuite testLog = parameters.getFsm().getTestSuite();
      field.set(model, testLog);
      log.debug("Value is now set to: "+testLog);
    } catch (IllegalAccessException e) {
      //TODO: proper error handling
      e.printStackTrace();
    }
  }
}
