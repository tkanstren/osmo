package osmo.tester.parser;

import osmo.tester.generator.testlog.TestLog;
import osmo.tester.model.FSM;
import osmo.tester.model.Requirements;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class ParserParameters {
  private FSM fsm = null;
  private Object model = null;
  private Object annotation = null;
  private TestLog testLog = null;
  private Requirements requirements = null;
  private Field field = null;
  private Method method = null;

  public FSM getFsm() {
    return fsm;
  }

  public void setFsm(FSM fsm) {
    this.fsm = fsm;
  }

  public Object getModel() {
    return model;
  }

  public void setModel(Object model) {
    this.model = model;
  }

  public Object getAnnotation() {
    return annotation;
  }

  public void setAnnotation(Object annotation) {
    this.annotation = annotation;
  }

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
    this.field = field;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }
}
