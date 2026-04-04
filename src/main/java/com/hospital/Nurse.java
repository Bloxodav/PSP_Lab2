package com.hospital;

public class Nurse extends Person {

    private String shift;

    public Nurse(String name, int age, String department, String shift) {
        super(name, age, department, "com.hospital.Nurse");
        this.shift = shift;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public String getInfo() {
        return String.format("[Медсестра] Имя: %-20s | Возраст: %3d | Отделение: %-15s | Смена: %s",
                getName(), getAge(), getDepartment(), shift);
    }

    @Override
    public String toFileString() {
        return "com.hospital.Nurse;" + getName() + ";" + getAge() + ";" + getDepartment() + ";" + shift;
    }
}