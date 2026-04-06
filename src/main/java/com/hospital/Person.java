package com.hospital;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public abstract class Person {

    private String name;
    private int age;
    private String department;
    private String role;

    public abstract String getInfo();

    public abstract String toFileString();

    @Override
    public String toString() {
        return getInfo();
    }
}