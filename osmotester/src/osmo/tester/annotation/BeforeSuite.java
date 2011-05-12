package osmo.tester.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation defines that the annotated method should be executed before test generation has been started
 * (only once before the whole suite, not before each test case).
 *
 * @author Teemu Kanstren
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeSuite {
}
