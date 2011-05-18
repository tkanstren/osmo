package osmo.tester.parser;

import osmo.tester.annotation.RequirementsField;
import osmo.tester.model.Requirements;

import java.lang.reflect.Field;

/**
 * Parses {@link RequirementsField} annotations from the given model object.
 * 
 * @author Teemu Kanstren
 */
public class RequirementsParser implements AnnotationParser {
  @Override
  public void parse(ParserParameters parameters) {
    RequirementsField annotation = (RequirementsField) parameters.getAnnotation();
    try {
      Field field = parameters.getField();
      //to enable setting private fields
      field.setAccessible(true);
      Object model = parameters.getModel();
      Requirements requirements = (Requirements) field.get(model);
      parameters.getFsm().setRequirements(requirements);
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
