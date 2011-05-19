package osmo.tester.parser;

import osmo.tester.annotation.After;
import osmo.tester.annotation.AfterSuite;
import osmo.tester.annotation.Before;
import osmo.tester.annotation.BeforeSuite;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.RequirementsField;
import osmo.tester.annotation.TestSuiteField;
import osmo.tester.annotation.Transition;
import osmo.tester.log.Logger;
import osmo.tester.model.FSM;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * The main parser that takes the given model object and parses it for specific registered annotations,
 * passes these to specific {@link AnnotationParser} implementations to update the {@link FSM} representation
 * accordign to the information for the specific annotation.
 * 
 * @author Teemu Kanstren
 */
public class MainParser {
  private static Logger log = new Logger(MainParser.class);
  /** Key = Annotation type, Value = The parser object for that annotation. */
  private final Map<Class<? extends Annotation>, AnnotationParser> parsers = new HashMap<Class<? extends Annotation>, AnnotationParser>();

  public MainParser() {
    //first we set up the parser objects for the different annotation types
    parsers.put(Transition.class, new TransitionParser());
    parsers.put(Guard.class, new GuardParser());
    parsers.put(After.class, new AfterParser());
    parsers.put(Before.class, new BeforeParser());
    parsers.put(AfterSuite.class, new AfterSuiteParser());
    parsers.put(BeforeSuite.class, new BeforeSuiteParser());
    parsers.put(TestSuiteField.class, new TestSuiteParser());
    parsers.put(RequirementsField.class, new RequirementsParser());
  }

  /**
   * Initiates parsing the given model object for the annotation that define the finite state machine (FSM) aspects
   * of the test model.
   *
   * @param obj The test model object.
   * @return The FSM object created from the given model object that can be used for test generation.
   */
  public FSM parse(Object obj) {
    log.debug("parsing");
    FSM fsm = new FSM(obj);
    //first we check any annotated fields that are relevant
    String errors = parseFields(fsm, obj);
    //next we check any annotated methods that are relevant
    errors += parseMethods(fsm, obj);
    //finally we check that the generated FSM itself is valid
    fsm.check(errors);
    return fsm;
  }

  /**
   * Parse the relevant annotated fields and pass these to correct {@link AnnotationParser} objects.
   *
   * @param fsm The test model object to be updated according to the parsed information.
   * @param obj The model object that contains the annotations and fields/executable methods for test generation.
   */
  private String parseFields(FSM fsm, Object obj) {
    //first we find all declared fields of any scope and type (private, protected, ...)
    Field[] fields = obj.getClass().getDeclaredFields();
    log.debug("fields "+fields.length);
    //next we create the parameter object and insert the common parameters
    ParserParameters parameters = new ParserParameters();
    parameters.setFsm(fsm);
    parameters.setModel(obj);
    String errors = "";
    //now we loop through all fields defined in the model object
    for (Field field : fields) {
      log.debug("field:"+field);
      //set the field to be accessible from the parser objects
      parameters.setField(field);
      Annotation[] annotations = field.getAnnotations();
      //loop through all defined annotations for each field
      for (Annotation annotation : annotations) {
        Class<? extends Annotation> annotationClass = annotation.annotationType();
        log.debug("class:"+annotationClass);
        AnnotationParser parser = parsers.get(annotationClass);
        if (parser == null) {
          //unsupported annotation (e.g. for some completely different aspect)
          continue;
        }
        log.debug("parser:"+parser);
        //set the annotation itself as a parameter to the used parser object
        parameters.setAnnotation(annotation);
        //and finally parse it
        errors += parser.parse(parameters);
      }
    }
    return errors;
  }

  /**
   * Parse the relevant annotated methods and pass these to correct {@link AnnotationParser} objects.
   *
   * @param fsm The test model object to be updated according to the parsed information.
   * @param obj The model object that contains the annotations and fields/executable methods for test generation.
   */
  private String parseMethods(FSM fsm, Object obj) {
    //first we get all methods defined in the test model object (also all scopes -> private, protected, ...)
    Method[] methods = obj.getClass().getMethods();
    log.debug("methods "+methods.length);
    //TODO: check that there are some methods to execute
    //construct and store common parameters first for all method parsers, update the rest each time
    ParserParameters parameters = new ParserParameters();
    parameters.setFsm(fsm);
    parameters.setModel(obj);
    String errors = "";
    //loop through all the methods defined in the given object
    for (Method method : methods) {
      log.debug("method:"+method);
      parameters.setMethod(method);
      Annotation[] annotations = method.getAnnotations();
      //check all annotations for supported ones, use the given object to process them
      for (Annotation annotation : annotations) {
        Class<? extends Annotation> annotationClass = annotation.annotationType();
        log.debug("class:"+annotationClass);
        AnnotationParser parser = parsers.get(annotationClass);
        if (parser == null) {
          //unsupported annotation (e.g. for some completely different aspect)
          continue;
        }
        log.debug("parser:"+parser);
        //set the annotation itself as a parameter to the used parser object
        parameters.setAnnotation(annotation);
        //and finally parse it
        errors += parser.parse(parameters);
      }
    }
    return errors;
  }
}
