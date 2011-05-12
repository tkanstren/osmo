package osmo.tester.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation describes the requirements field in the model object passed to the OSMOTester.generate() method.
 * This field must be of type Requirements. If a correct field is found and has this annotation, the tester will
 * pick up the referenced object and use that to provide a list of expected requirements that the test generator
 * should aim to cover. If the field is null or of wrong type, an exception will be thrown and test generation
 * will not commence. For more information on defining the requirements, see the Requirements class.
 *
 * @see osmo.tester.model.Requirements
 *
 * @author Teemu Kanstren
 */
//TODO: add check that there is at most one requirements field (+test it)
//TODO: add check that the requirements field is of the expected type (+test it)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirementsField {
}
