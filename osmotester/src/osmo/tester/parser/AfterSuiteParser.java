package osmo.tester.parser;

import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class AfterSuiteParser implements AnnotationParser {
  private static Logger log = new Logger(AfterSuiteParser.class);

  public void parse(ParserParameters parameters) {
    AfterSuite after = (AfterSuite) parameters.getAnnotation();
    Method method = parameters.getMethod();
    log.debug("found @AfterSuite method:"+method.getName());
    parameters.getFsm().addAfterSuite(method);
  }
}
