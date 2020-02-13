package ru.artemaa.demo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConsistentProperties
public class ObjectToValidate {
    private String property;
    private String anotherProperty;
}
