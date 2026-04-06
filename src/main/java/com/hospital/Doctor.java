package com.hospital;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Doctor extends Person {

    private String specialization;

    public Doctor(String name, int age, String department, String specialization) {
        super(name, age, department, "com.hospital.Doctor");
        this.specialization = specialization;
    }

    @Override
    public String getInfo() {
        return String.format("[Врач] Имя: %-20s | Возраст: %3d | Отделение: %-15s | Специализация: %s",
                getName(), getAge(), getDepartment(), specialization);
    }

    @Override
    public String toFileString() {
        return "com.hospital.Doctor;" + getName() + ";" + getAge() + ";" + getDepartment() + ";" + specialization;
    }
}