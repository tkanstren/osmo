package osmo.tester.model.dataflow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static osmo.tester.TestUtils.oneOf;

/**
 * @author Teemu Kanstren
 */
public class ObjectSetInvariant<T> {
  private List<T> options = new ArrayList<T>();
  private InputStrategy strategy = InputStrategy.RANDOM;
  //index for next item if using ORDERED_LOOP. Using this instead of iterator to allow modification of options in runtime
  private int next = 0;

  public ObjectSetInvariant() {
  }

  public ObjectSetInvariant(InputStrategy strategy) {
    this.strategy = strategy;
  }

  public void addOption(T option) {
    options.add(option);
  }

  public void removeOption(T option) {
    int index = options.indexOf(option);
    if (index <= next) {
      next--;
    }
    options.remove(option);
  }

  public boolean evaluate(T value) {
    return options.contains(value);
  }

  public T input() {
    if (options.size() == 0) {
      throw new IllegalStateException("No value to provide (add some options).");
    }
    if (strategy == InputStrategy.ORDERED_LOOP) {
      if (next >= options.size()) {
        next = 0;
      }
      return options.get(next++);
    }
    return oneOf(options);
  }
}
