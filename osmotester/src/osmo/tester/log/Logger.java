package osmo.tester.log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

/**
 * @author Teemu Kanstren
 */
public class Logger {
  private java.util.logging.Logger logger;
  public static boolean debug = false;

  public Logger(Class clazz) {
    String name = "";
    String p = clazz.getPackage().getName();
    String[] ps = p.split("\\.");
    for (String s : ps) {
      name += s.charAt(0)+".";
    }
    name += clazz.getSimpleName();
    init(name);
  }

  private void init(String name) {
    logger = java.util.logging.Logger.getLogger(name);
    Level level = Level.OFF;
    if (debug) {
      level = Level.ALL;
    }
    logger.setLevel(level);
    LogHandler console = new LogHandler();
    console.setFormatter(new LogFormatter());
    console.setLevel(level);
    logger.addHandler(console);
    String filename = "osmotester.log";
    try {
      FileHandler file = new FileHandler(filename, false);
      logger.addHandler(file);
      file.setFormatter(new LogFormatter());
      file.setLevel(level);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to initialize file '"+filename+"' for logging.");
    }
  }

  public void debug(String msg) {
//    System.out.println("hello:"+msg);
    logger.fine(msg);
  }

  public void error(String msg, Exception e) {
    logger.log(Level.SEVERE, msg, e);
  }
}
