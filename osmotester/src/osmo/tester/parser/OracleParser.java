package osmo.tester.parser;

import osmo.tester.annotation.Oracle;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;
import osmo.tester.model.FSMTransition;

import java.lang.reflect.Method;

/**
 * @author Teemu Kanstren
 */
public class OracleParser implements AnnotationParser {
  private static Logger log = new Logger(AfterParser.class);

  @Override
  public String parse(ParserParameters parameters) {
    Oracle oracle = (Oracle) parameters.getAnnotation();
    Method method = parameters.getMethod();
    FSM fsm = parameters.getFsm();
    String[] transitionNames = oracle.value();
    for (String name : transitionNames) {
      log.debug("Parsing oracle '"+name+"'");
      if (name.equals("all")) {
        fsm.addGenericOracle(method);
        //generic guards should not be have their own transition or it will fail the FSM check since it is a guard
        //without a transition
        //TODO: add check that no transition called "all" is allowed
        continue;
      }
      FSMTransition transition = fsm.createTransition(name);
      transition.addOracle(method);
    }
    return "";
  }
}
