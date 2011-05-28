package osmo.tester.model;

import osmo.tester.log.Logger;
import osmo.tester.parser.ParserParameters;
import sun.rmi.runtime.Log;

import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class InvocationTarget {
  private static Logger log = new Logger(InvocationTarget.class);
  /** The model object itself, implementing the actual transition methods etc. */
  private final Object modelObject;
  /** The method to be invoked on the model object. */
  private final Method method;
  private final String type;

  public InvocationTarget(ParserParameters parameters, Class type) {
    this.modelObject = parameters.getModel();
    this.method = parameters.getMethod();
    this.type = "@"+type.getName();
    log.debug("Found and created "+this.type+" method:"+method.getName());
  }

  public Object invoke() {
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
