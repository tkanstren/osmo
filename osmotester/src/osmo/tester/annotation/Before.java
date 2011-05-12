package osmo.tester.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation defines that the annotated method should be executed before each test case that is
 * generated.
 *
 * @author Teemu Kanstren
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
}
