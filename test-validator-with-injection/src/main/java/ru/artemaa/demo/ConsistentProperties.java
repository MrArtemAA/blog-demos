package ru.artemaa.demo;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConsistentPropertiesValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistentProperties {
    String message() default "Another Property is required";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
