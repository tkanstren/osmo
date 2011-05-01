package osmo.tester.parser;

import osmo.tester.annotation.Before;
import osmo.tester.annotation.Guard;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class BeforeParser implements AnnotationParser {
  private static Logger log = new Logger(BeforeParser.class);

  public void parse(FSM fsm, Method method, Annotation annotation) {
    Before before = (Before) annotation;
    log.debug("found @before method:"+method.getName());
    fsm.addBefore(method);
  }
}
