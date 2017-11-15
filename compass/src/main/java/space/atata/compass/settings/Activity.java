package space.atata.compass.settings;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by atata on 11/11/2017.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Activity {
    Class value() ;
}
