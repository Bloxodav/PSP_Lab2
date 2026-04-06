package com.hospital;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
public class Patient extends Person {

    private String diagnosis;

    public Patient(String name, int age, String department, String diagnosis) {
        super(name, age, department, "com.hospital.Patient");
        this.diagnosis = diagnosis;
    }

    @Override
    public String getInfo() {
        return String.format("[Пациент] Имя: %-20s | Возраст: %3d | Отделение: %-15s | Диагноз: %s",
                getName(), getAge(), getDepartment(), diagnosis);
    }

    @Override
    public String toFileString() {
        return "com.hospital.Patient;" + getName() + ";" + getAge() + ";" + getDepartment() + ";" + diagnosis;
    }
}