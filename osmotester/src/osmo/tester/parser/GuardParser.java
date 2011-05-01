package osmo.tester.parser;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class GuardParser implements AnnotationParser {
  private static Logger log = new Logger(GuardParser.class);

  public void parse(FSM fsm, Method method, Annotation annotation) {
    Guard g = (Guard) annotation;
    String transitionName = g.value();
    log.debug("found guard for transition: "+transitionName);
    FSMTransition transition = fsm.createTransition(transitionName);
    transition.addGuard(method);
  }
}
