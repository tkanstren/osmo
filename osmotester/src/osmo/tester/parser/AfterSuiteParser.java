package osmo.tester.parser;

import osmo.tester.annotation.AfterSuite;
import osmo.tester.log.Logger;

import java.lang.reflect.Method;

/**
 * Parses {@link AfterSuite} annotations from the given model object.
 * 
 * @author Teemu Kanstren
 */
public class AfterSuiteParser implements AnnotationParser {
  private static Logger log = new Logger(AfterSuiteParser.class);

  @Override
  public void parse(ParserParameters parameters) {
    AfterSuite after = (AfterSuite) parameters.getAnnotation();
    Method method = parameters.getMethod();
    log.debug("found @AfterSuite method:"+method.getName());
    parameters.getFsm().addAfterSuite(method);
  }
}
