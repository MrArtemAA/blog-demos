package ru.artemaa.demo;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorFactoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class ObjectToValidateUnitTest {
    private static Validator validator;
    private static ConsistentPropertiesValidator consistentPropertiesValidator;

    @BeforeAll
    static void setUpTest() {
        ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure()
                .constraintValidatorFactory(new CustomConstraintValidatorFactory())
                .buildValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();

        Config config = new Config();
        config.setPropertyValues(asList("value1", "value2"));
        consistentPropertiesValidator = new ConsistentPropertiesValidator(config);
    }

    @Test
    void validate() {
        ObjectToValidate objectToValidate = new ObjectToValidate();
        objectToValidate.setProperty("value");
        Set<ConstraintViolation<ObjectToValidate>> violations = validator.validate(objectToValidate);
        assertTrue(violations.isEmpty());

        objectToValidate.setProperty("value1");
        violations = validator.validate(objectToValidate);
        assertFalse(violations.isEmpty());
        ConstraintViolation<ObjectToValidate> violation = violations.iterator().next();
        assertEquals(ConsistentProperties.class, violation.getConstraintDescriptor().getAnnotation().annotationType());

        objectToValidate.setProperty("value1");
        objectToValidate.setAnotherProperty("another value");
        violations = validator.validate(objectToValidate);
        assertTrue(violations.isEmpty());
    }

    public static class CustomConstraintValidatorFactory implements ConstraintValidatorFactory {
        private final ConstraintValidatorFactory hibernateConstraintValidatorFactory = new ConstraintValidatorFactoryImpl();

        @Override
        public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
            if (ConsistentPropertiesValidator.class.equals(key)) {
                //noinspection unchecked
                return (T) consistentPropertiesValidator;
            }
            return hibernateConstraintValidatorFactory.getInstance(key);
        }

        @Override
        public void releaseInstance(ConstraintValidator<?, ?> instance) {
            hibernateConstraintValidatorFactory.releaseInstance(instance);
        }
    }
}