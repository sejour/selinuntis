package github.sejour.selinuntis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
    String value() default "";

    /**
     * affectsQueryCache indicates whether the parameter affects the query cache.
     */
    boolean affectsQueryCache() default false;
}
