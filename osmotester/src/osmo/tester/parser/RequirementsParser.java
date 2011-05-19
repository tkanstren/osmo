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
  private int count = 0;

  @Override
  public String parse(ParserParameters parameters) {
    count++;
    String errors = "";
    String name = "@"+RequirementsField.class.getSimpleName();
    if (count > 1) {
      errors += "Only one "+name+" allowed in the model.\n";
    }
    RequirementsField annotation = (RequirementsField) parameters.getAnnotation();
    try {
      Field field = parameters.getField();
      Class<?> type = field.getType();
      if (type != Requirements.class) {
        errors += name+" class must be of type "+Requirements.class.getName()+". Was "+type.getName()+".\n";
        return errors;
      }
      //to enable setting private fields
      field.setAccessible(true);
      Object model = parameters.getModel();
      Requirements requirements = (Requirements) field.get(model);
      if (requirements == null) {
        errors += name +" value was null, which is not allowed.\n";
        return errors;
      }
      parameters.getFsm().setRequirements(requirements);
    } catch (IllegalAccessException e) {
      throw new RuntimeException("Unable to parse/set "+name, e);
    }
    return errors;
  }
}
