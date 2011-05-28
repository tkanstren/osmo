package osmo.tester.generator;

import osmo.tester.generator.GenerationListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Teemu Kanstren
 */
public class GenerationListenerList implements GenerationListener {
  private Collection<GenerationListener> listeners = new ArrayList<GenerationListener>();

  public void addListener(GenerationListener listener) {
    listeners.add(listener);
  }

  @Override
  public void guard(String name) {
    for (GenerationListener listener : listeners) {
      listener.guard(name);
    }
  }

  @Override
  public void transition(String name) {
    for (GenerationListener listener : listeners) {
      listener.transition(name);
    }
  }

  @Override
  public void oracle(String name) {
    for (GenerationListener listener : listeners) {
      listener.oracle(name);
    }
  }

  @Override
  public void testStarted() {
    for (GenerationListener listener : listeners) {
      listener.testStarted();
    }
  }

  @Override
  public void testEnded() {
    for (GenerationListener listener : listeners) {
      listener.testEnded();
    }
  }

  @Override
  public void suiteStarted() {
    for (GenerationListener listener : listeners) {
      listener.suiteStarted();
    }
  }

  @Override
  public void suiteEnded() {
    for (GenerationListener listener : listeners) {
      listener.suiteEnded();
    }
  }
}
