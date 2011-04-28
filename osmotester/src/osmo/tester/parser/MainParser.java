package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class MainParser {
  public FSM parse(Object obj) {
    FSM fsm = new FSM(obj);
    Method[] methods = obj.getClass().getMethods();
    //TODO: check that there are some methods to execute
    for (Method method : methods) {
      Annotation[] annotations = method.getAnnotations();
      for (Annotation annotation : annotations) {
        if (annotation instanceof Transition) {
          Transition t = (Transition) annotation;
          FSMTransition transition = fsm.createTransition(t.value());
          transition.setTransition(method);
        }
        if (annotation instanceof Guard) {
          Guard g = (Guard) annotation;
          FSMTransition transition = fsm.createTransition(g.value());
          transition.addGuard(method);
        }
        if (annotation instanceof After) {
          After after = (After) annotation;
          fsm.addAfter(method);
        }
        if (annotation instanceof Before) {
          Before before = (Before) annotation;
          fsm.addBefore(method);
        }
        if (annotation instanceof AfterSuite) {
          AfterSuite after = (AfterSuite) annotation;
          fsm.addAfterSuite(method);
        }
        if (annotation instanceof BeforeSuite) {
          BeforeSuite before = (BeforeSuite) annotation;
          fsm.addBeforeSuite(method);
        }
      }
    }
    fsm.check();
    return fsm;
  }
}
