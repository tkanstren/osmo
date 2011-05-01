package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.annotation.Guard;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class AfterParser implements AnnotationParser {
  private static Logger log = new Logger(AfterParser.class);

  public void parse(FSM fsm, Method method, Annotation annotation) {
    After after = (After) annotation;
    log.debug("found @After method:"+method.getName());
    fsm.addAfter(method);
  }
}
