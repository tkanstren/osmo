package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.log.Logger;

import java.lang.reflect.Method;

/**
 * Parses {@link After} annotations from the given model object.
 * 
 * @author Teemu Kanstren
 */
public class AfterParser implements AnnotationParser {
  private static Logger log = new Logger(AfterParser.class);

  @Override
  public void parse(ParserParameters parameters) {
    After after = (After) parameters.getAnnotation();
    //todo: check there is a method (contract)
    Method method = parameters.getMethod();
    log.debug("found @After method:"+method.getName());
    parameters.getFsm().addAfter(method);
  }
}
