package dk.lbloft.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Builder {
	Class<?> value() default Object.class;
	Class<? extends RuntimeBuilder<? extends BaseCo>> builder() default GenericBuilder.class;
}
