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
    Method method = parameters.getMethod();
    //TODO: put constants in their own class
    if (transitionName.equals("all")) {
      fsm.addGenericGuard(method);
      //generic guards should not be have their own transition or it will fail the FSM check since it is a guard
      //without a transition
      //TODO: add check that no transition called "all" is allowed
      return;
    }
    FSMTransition transition = fsm.createTransition(transitionName);
    transition.addGuard(method);
  }
}
