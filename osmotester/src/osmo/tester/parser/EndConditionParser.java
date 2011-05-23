package osmo.tester.parser;

import osmo.tester.annotation.EndCondition;
import osmo.tester.log.Logger;
import osmo.tester.model.InvocationTarget;

import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class EndConditionParser implements AnnotationParser {
  private static Logger log = new Logger(EndConditionParser.class);

  @Override
  public String parse(ParserParameters parameters) {
    EndCondition ec = (EndCondition) parameters.getAnnotation();
    Method method = parameters.getMethod();
    log.debug("found @EndCondition method:"+method.getName());
    parameters.getFsm().addEndCondition(new InvocationTarget(parameters));
    return "";
  }
}
