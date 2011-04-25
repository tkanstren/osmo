package osmo.tester.parser;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Teemu Kanstren
 */
public class MainParser {
  public FSM parse(Class clazz) {
    FSM fsm = new FSM();
    Method[] methods = clazz.getMethods();
    for (Method method : methods) {
      System.out.println("m:"+method);
      Annotation[] annotations = method.getAnnotations();
      for (Annotation annotation : annotations) {
        System.out.println("a:"+annotation);
        if (annotation instanceof Transition) {
          Transition t = (Transition) annotation;
          FSMTransition transition = fsm.createTransition(t.value());
          transition.setTransition(method);
        }
        if (annotation instanceof Guard) {
          Guard t = (Guard) annotation;
          FSMTransition transition = fsm.createTransition(t.value());
          transition.addGuard(method);
        }
      }
    }
    fsm.check();
    return fsm;
  }
}
