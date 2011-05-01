package osmo.tester.parser;

import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class AfterSuiteParser implements AnnotationParser {
  private static Logger log = new Logger(AfterSuiteParser.class);

  public void parse(FSM fsm, Method method, Annotation annotation) {
    AfterSuite after = (AfterSuite) annotation;
    log.debug("found @AfterSuite method:"+method.getName());
    fsm.addAfterSuite(method);
  }
}
