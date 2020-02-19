package ru.artemaa.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ObjectToValidateFailingUnitTest {
    private static Validator validator;

    @BeforeAll
    static void setUpTest() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
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
}