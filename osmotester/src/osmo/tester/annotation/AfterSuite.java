package osmo.tester.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation defines that the annotated method should be executed after test generation has finished
 * (all tests in the suite have been generated).
 *
 * @author Teemu Kanstren
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterSuite {
}
