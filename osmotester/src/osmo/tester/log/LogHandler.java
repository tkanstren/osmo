package osmo.tester.log;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * @author Teemu Kanstren
 */
public class LogHandler extends Handler {
  private PrintStream out = System.out;
  private LogFormatter formatter = new LogFormatter();

  @Override
  public void publish(LogRecord record) {
    out.print(formatter.format(record));
  }

  @Override
  public void flush() {
    //system.out handles itself
  }

  @Override
  public void close() throws SecurityException {
    //system.out handles itself
  }
}
