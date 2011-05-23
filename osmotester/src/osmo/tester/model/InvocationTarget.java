package osmo.tester.model;

import osmo.tester.parser.ParserParameters;

import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class InvocationTarget {
  /** The model object itself, implementing the actual transition methods etc. */
  private final Object modelObject;
  /** The method to be invoked on the model object. */
  private final Method method;

  public InvocationTarget(ParserParameters parameters) {
    this(parameters.getModel(), parameters.getMethod());
  }

  private InvocationTarget(Object modelObject, Method method) {
    this.modelObject = modelObject;
    this.method = method;
  }

  public Object invoke(String type) {
    try {
      return method.invoke(modelObject);
    } catch (Exception e) {
      throw new RuntimeException("Failed to invoke "+type+" method on the model object.", e);
    }
  }

  public Method getMethod() {
    return method;
  }
}
