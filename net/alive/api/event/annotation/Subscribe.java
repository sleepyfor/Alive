package net.alive.api.event.annotation;

import net.alive.api.event.priority.Priority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SoonTM
 * @since 7/19/2019
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Subscribe {
    Priority priority() default Priority.NORMAL;
}
