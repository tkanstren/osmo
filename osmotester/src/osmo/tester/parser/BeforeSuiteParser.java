package osmo.tester.parser;

import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class BeforeSuiteParser implements AnnotationParser {
  private static Logger log = new Logger(BeforeSuiteParser.class);

  public void parse(FSM fsm, Method method, Annotation annotation) {
    BeforeSuite before = (BeforeSuite) annotation;
    log.debug("found @BeforeSuite method:"+method.getName());
    fsm.addBeforeSuite(method);
  }
}
