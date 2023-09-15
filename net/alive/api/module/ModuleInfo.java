package net.alive.api.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name() default "INVALID NAME";
    int keyBind();
    String displayName() default "INVALID NAME";
    Category category();
}
