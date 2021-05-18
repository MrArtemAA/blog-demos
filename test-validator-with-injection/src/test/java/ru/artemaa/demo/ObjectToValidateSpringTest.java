package ru.artemaa.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = {
        ConsistentPropertiesValidator.class,
        ValidationAutoConfiguration.class
})
public class ObjectToValidateSpringTest {
    @Autowired
    private Validator validator;

    @MockBean
    private Config config;

    @Test
    void validate() {
        given(config.getPropertyValues())
                .willReturn(asList("value1", "value2"));

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
