package space.atata.compass.settings;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by atata on 11/11/2017.
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Params {
}
