package github.sejour.selinuntis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface QueryCache {
    /**
     * ttlSeconds is the cache retention time in seconds.
     * If it is 0 or less, the default setting is applied.
     */
    long ttlSeconds() default 0;
}
