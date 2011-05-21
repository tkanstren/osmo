package osmo.tester.generator;

/**
 * @author Teemu Kanstren
 */
public interface GenerationListener {
  public void guard(String name);
  public void transition(String name);
  public void oracle(String name);
  public void script(String name);
  public void testStarted();
  public void testEnded();
}
