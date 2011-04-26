package osmo.tester.parser;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;

/**
 * @author Teemu Kanstren
 */
public class TestModel1 {
  @Transition("hello")
  public void transition1() {

  }

  @Transition("world")
  public void epix() {

  }

  @Guard("world")
  public boolean listCheck() {
    return false;
  }

  @Guard("world")
  public boolean listCheck2() {
    return false;
  }

  @Transition("epixx")
  public void epixx() {

  }

  @Guard("epixx")
  public boolean kitted() {
    return false;
  }
}
