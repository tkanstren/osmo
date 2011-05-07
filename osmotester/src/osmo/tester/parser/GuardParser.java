package osmo.tester.parser;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class GuardParser implements AnnotationParser {
  private static Logger log = new Logger(GuardParser.class);

  public void parse(ParserParameters parameters) {
    Guard g = (Guard) parameters.getAnnotation();
    String transitionName = g.value();
    log.debug("found guard for transition: "+transitionName);
    FSM fsm = parameters.getFsm();
    FSMTransition transition = fsm.createTransition(transitionName);
    Method method = parameters.getMethod();
    transition.addGuard(method);
  }
}
