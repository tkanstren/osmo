package osmo.tester.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Teemu Kanstren
 */
public class FSMTransition {
  private final String name;
  private final Collection<Method> guards = new ArrayList<Method>();
  private Method transition = null;

  protected FSMTransition(String name) {
    this.name = name;
  }

  public void addGuard(Method method) {
    guards.add(method);
  }

  public String getName() {
    return name;
  }

  public Collection<Method> getGuards() {
    return guards;
  }

  public Method getTransition() {
    return transition;
  }

  public void setTransition(Method transition) {
    this.transition = transition;
  }
}
