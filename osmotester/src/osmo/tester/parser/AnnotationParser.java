package osmo.tester.parser;

import osmo.tester.model.FSM;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public interface AnnotationParser {
  public void parse(FSM fsm, Method method, Annotation annotation);
}
