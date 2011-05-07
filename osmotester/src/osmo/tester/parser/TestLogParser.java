package osmo.tester.parser;

import osmo.tester.annotation.TestLogField;
import osmo.tester.generator.testlog.TestLog;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class TestLogParser implements AnnotationParser {
  private static Logger log = new Logger(TestLogParser.class);

  public void parse(ParserParameters parameters) {
    TestLogField annotation = (TestLogField) parameters.getAnnotation();
    log.debug("TestLogField processing");
    try {
      Field field = parameters.getField();
      //we bypass the private etc. modifiers to access it
      field.setAccessible(true);
      Object model = parameters.getModel();
      //TODO: add contract: testlog cannot be null
      TestLog testLog = parameters.getTestLog();
      field.set(model, testLog);
      log.debug("Value is now set to: "+testLog);
    } catch (IllegalAccessException e) {
      //TODO: proper error handling
      e.printStackTrace();
    }
  }
}
