package org.mufassa.model.json;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface defining a tag that can be placed at class attributes to avoid
 * them from being serialized into Json format (when saving a scenario)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IgnoreInJson {

}
