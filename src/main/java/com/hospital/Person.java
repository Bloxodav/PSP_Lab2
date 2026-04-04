package com.hospital;

public abstract class Person {

    private String name;
    private int age;
    private String department;
    private String role;

    public Person(String name, int age, String department, String role) {
        this.name = name;
        this.age = age;
        this.department = department;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public abstract String getInfo();

    public abstract String toFileString();

    @Override
    public String toString() {return getInfo();}
}