package osmo.tester.model.dataflow;

import osmo.tester.log.Logger;

import java.util.ArrayList;
import java.util.Collection;

import static osmo.tester.TestUtils.*;

/**
 * @author Teemu Kanstren
 */
public abstract class NumericInvariant<T extends Number> {
  private static final Logger log = new Logger(NumericInvariant.class);
  private ObjectSetInvariant<Partition> partitions = new ObjectSetInvariant<Partition>();
  private InputStrategy strategy = InputStrategy.RANDOM;
  protected Collection<T> history = new ArrayList<T>();

  public void setStrategy(InputStrategy strategy) {
    this.strategy = strategy;
    partitions.setStrategy(strategy);
  }

  //test for update of main partition based on added partitions
  public void addPartition(T min, T max) {
    log.debug("Adding partition min("+min+") max("+max+")");
    if (min.doubleValue() > max.doubleValue()) {
      throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
    }
    partitions.addOption(new Partition(min, max));
  }

  public void removePartition(T min, T max) {
    log.debug("Removing partition min("+min+") max("+max+")");
    partitions.removeOption(new Partition(min, max));
  }

  //TODO: test with 0 partitions
  public Partition nextInterval() {
    if (strategy != InputStrategy.OPTIMIZED_RANDOM) {
      Partition partition = partitions.input();
      log.debug("Next interval "+partition);
      return partition;
    }
    return optimizedRandomPartition();
  }

  private Partition optimizedRandomPartition() {
    log.debug("Optimized random partition choice start");
    Collection<Partition> options = partitions.getAll();
    if (options.size() == 1) {
      Partition partition = options.iterator().next();
      log.debug("Single partition found, returning it:"+partition);
      return partition;
    }
    int min = Integer.MAX_VALUE;
    for (Partition option : options) {
      int count = coverageFor(option);
      log.debug("Coverage for "+option+":"+count);
      if (count < min) {
        min = count;
      }
    }
    log.debug("Min coverage:"+min);
    Collection<Partition> currentOptions = new ArrayList<Partition>();
    for (Partition option : options) {
      int count = coverageFor(option);
      log.debug("Coverage for current option "+option+":"+count);
      if (count == min) {
        currentOptions.add(option);
      }
    }
    return oneOf(currentOptions);
  }

  private int coverageFor(Partition partition) {
    int count = 0;
    for (Number value : history) {
      if (partition.contains(value)) {
        count++;
      }
    }
    return count;
  }

  protected void validate() {
    if (partitions.size() == 0) {
      throw new IllegalStateException("No partitions defined. Add some to use this for something.");
    }
  }

  public abstract T input();
/*
  public int nextI() {
    Partition i = nextInterval();
    int min = i.min().intValue();
    int max = i.max().intValue();
    int value = cInt(min, max);
    history.add(value);
    return value;
  }

  public long nextL() {
    Partition i = nextInterval();
    long min = i.min().longValue();
    long max = i.max().longValue();
    long value = cLong(min, max);
    history.add(value);
    return value;
  }

  public float nextF() {
    Partition i = nextInterval();
    float min = i.min().floatValue();
    float max = i.max().floatValue();
    float value = cFloat(min, max);
    history.add(value);
    return value;
  }

  public double nextD() {
    Partition i = nextInterval();
    double min = i.min().doubleValue();
    double max = i.max().doubleValue();
    double value = cDouble(min, max);
    history.add(value);
    return value;
  }
*/
  public boolean evaluate(T value) {
    Collection<Partition> partitions = this.partitions.getAll();
    log.debug("Evaluating value:"+value);
    for (Partition partition : partitions) {
      log.debug("Checking partition:"+partition);
      if (partition.contains(value)) {
        log.debug("Found match");
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "NumericInvariant{" +
            "strategy=" + strategy +
            ", partitions=" + partitions +
            '}';
  }
}
