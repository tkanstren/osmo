package osmo.tester.parser;

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
public class TransitionParser implements AnnotationParser {
  private static Logger log = new Logger(TransitionParser.class);

  public void parse(ParserParameters parameters) {
    Transition t = (Transition) parameters.getAnnotation();
    String name = t.value();
    log.debug("Found transition: "+name);
    FSMTransition transition = parameters.getFsm().createTransition(name);
    transition.setTransition(parameters.getMethod());
  }
}
