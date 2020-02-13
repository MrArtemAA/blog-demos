package ru.artemaa.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ObjectToValidateSpringBootTest {
    @Autowired
    private Validator validator;

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