package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Teemu Kanstren
 */
public class MainParser {
  private static Logger log = new Logger(MainParser.class);
  private final Map<Class<? extends Annotation>, AnnotationParser> parsers = new HashMap<Class<? extends Annotation>, AnnotationParser>();

  public MainParser() {
    parsers.put(Transition.class, new TransitionParser());
    parsers.put(Guard.class, new GuardParser());
    parsers.put(After.class, new AfterParser());
    parsers.put(Before.class, new BeforeParser());
    parsers.put(AfterSuite.class, new AfterSuiteParser());
    parsers.put(BeforeSuite.class, new BeforeSuiteParser());
  }

  public FSM parse(Object obj) {
    log.debug("parsing");
    FSM fsm = new FSM(obj);
    Method[] methods = obj.getClass().getMethods();
    log.debug("methods "+methods.length);
    //TODO: check that there are some methods to execute
    for (Method method : methods) {
      log.debug("method:"+method);
      Annotation[] annotations = method.getAnnotations();
      for (Annotation annotation : annotations) {
        Class<? extends Annotation> annotationClass = annotation.annotationType();
        log.debug("class:"+annotationClass);
        AnnotationParser parser = parsers.get(annotationClass);
        if (parser == null) {
          continue;
        }
        log.debug("parser:"+parser);
        parser.parse(fsm, method, annotation);
      }
    }
    fsm.check();
    return fsm;
  }
}
