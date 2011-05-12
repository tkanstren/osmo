package osmo.tester.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation defines that the annotated method should be executed after each test case that has been
 * generated.
 *
 * @author Teemu Kanstren
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface After {
}
