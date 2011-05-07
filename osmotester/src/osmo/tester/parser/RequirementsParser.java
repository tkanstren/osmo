package osmo.tester.parser;

import osmo.tester.annotation.Requirements;
import osmo.tester.model.FSM;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class RequirementsParser implements AnnotationParser {
  public void parse(ParserParameters parameters) {
    Requirements requirements = (Requirements) parameters.getAnnotation();
    try {
      Field field = parameters.getField();
      Object model = parameters.getModel();
      String[] reqs = (String[]) field.get(model);
      FSM fsm = parameters.getFsm();
      for (String req : reqs) {
        fsm.addRequirement(req);
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
