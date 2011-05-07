package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.annotation.Guard;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class AfterParser implements AnnotationParser {
  private static Logger log = new Logger(AfterParser.class);

  public void parse(ParserParameters parameters) {
    After after = (After) parameters.getAnnotation();
    //todo: check there is a method (contract)
    Method method = parameters.getMethod();
    log.debug("found @After method:"+method.getName());
    parameters.getFsm().addAfter(method);
  }
}
