package osmo.tester.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author Teemu Kanstren
 */
public class LogFormatter extends Formatter {
//  private final static String format = "{0,date} {0,time}";
  private final static String format = "{0,time}";
  private MessageFormat formatter;
  private Object args[] = new Object[1];
  private Date date = new Date();

  public LogFormatter() {
    formatter = new MessageFormat(format);
    args[0] = date;
  }

  public String format(LogRecord record) {
    StringBuffer line = new StringBuffer();
    try {
      long time = record.getMillis();
      date.setTime(time);
      formatter.format(args, line, null);
      line.append(" ");
      line.append(record.getLoggerName()).append(" - ");
      line.append(formatMessage(record)).append("\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return line.toString();
  }
}
