package osmo.tester.model.dataflow;

import static osmo.tester.TestUtils.*;
/**
 * @author Teemu Kanstren
 */
public class DoubleInvariant extends NumericInvariant<Double> {
  @Override
  public Double input() {
    validate();
    Partition i = nextInterval();
    double min = i.min().doubleValue();
    double max = i.max().doubleValue();
    double value = cDouble(min, max);
    history.add(value);
    return value;
  }
}
