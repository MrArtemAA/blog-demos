package ru.artemaa.demo;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConsistentPropertiesValidator implements ConstraintValidator<ConsistentProperties, ObjectToValidate> {
   private final Config config;

   public ConsistentPropertiesValidator(Config config) {
      this.config = config;
   }

   public boolean isValid(ObjectToValidate obj, ConstraintValidatorContext context) {
      if (obj == null) {
         return true;
      }
      boolean shouldHaveAnotherProperty = config.getPropertyValues().contains(obj.getProperty());
      return !(shouldHaveAnotherProperty && StringUtils.isEmpty(obj.getAnotherProperty()));
   }
}
