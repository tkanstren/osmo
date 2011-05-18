package osmo.tester.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation describes a field in the model object for providing access to the test generation history
 * while the generator is processing the model to generate tests. Any field annotated with this annotation
 * will be set by the generator before starting test generation to contain a reference to the TestLog object
 * that gives the model object itself access to the test generation history of the generator.
 *
 * If the annotated field is not of type TestLog, it an exception will be produced and generation will not commence.
 * If this field is set to a value in the test model before the model object is passed to the generator
 * (OSMOTester.generate()) the parser will throw an exception and generation does not commence. Therefore
 * the field should always be given a null value. After the value is set by the parser/generator, the set object
 * will be updated but if the reference is changed it will have no impact on the test generation other than
 * the model object not having access to the correct history object through this reference.
 *
 * @see osmo.tester.generator.testsuite.TestSuite
 *
 * @author Teemu Kanstren
 */ 
//TODO: add tests for someone having set the testsuite object to non-null value+wrong data type
//TODO: check what constraints can be set on an annotation (only on field, only of specific type..)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestSuiteField {
}
