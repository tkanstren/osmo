package osmo.tester.parser;

import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Transition;

/**
 * @author Teemu Kanstren
 */
public class TestModel {
  @Transition("hello")
  public void transition1() {

  }

  @Transition("world")
  public void epix() {

  }

  //test: wrong return type
  //test:0-2 guards/transition
  @Guard("world")
  public boolean listCheck() {
    return false;
  }

  
}
