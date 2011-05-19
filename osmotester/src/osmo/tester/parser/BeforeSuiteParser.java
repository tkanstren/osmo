package osmo.tester.parser;

import osmo.tester.annotation.BeforeSuite;
import osmo.tester.log.Logger;

import java.lang.reflect.Method;

/**
 * Parses {@link BeforeSuite} annotations from the given model object.
 * 
 * @author Teemu Kanstren
 */
public class BeforeSuiteParser implements AnnotationParser {
  private static Logger log = new Logger(BeforeSuiteParser.class);

  @Override
  public String parse(ParserParameters parameters) {
    BeforeSuite before = (BeforeSuite) parameters.getAnnotation();
    Method method = parameters.getMethod();
    log.debug("found @BeforeSuite method:"+method.getName());
    parameters.getFsm().addBeforeSuite(method);
    return "";
  }
}
