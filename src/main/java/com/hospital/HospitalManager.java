package com.hospital;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.*;

public class HospitalManager {

    private static final Logger logger = Logger.getLogger(HospitalManager.class);

    private List<Person> people = new ArrayList<>();

    private static final String FILE_NAME = "hospital_data.txt";

    public void addPatient(String name, int age, String department, String diagnosis) {
        Patient patient = new Patient(name, age, department, diagnosis);
        people.add(patient);
        logger.info("Добавлен пациент: " + name);
        System.out.println("Пациент добавлен: " + patient.getInfo());
    }

    public void addDoctor(String name, int age, String department, String specialization) {
        Doctor doctor = new Doctor(name, age, department, specialization);
        people.add(doctor);
        logger.info("Добавлен врач: " + name);
        System.out.println("Врач добавлен: " + doctor.getInfo());
    }

    public void addNurse(String name, int age, String department, String shift) {
        Nurse nurse = new Nurse(name, age, department, shift);
        people.add(nurse);
        logger.info("Добавлена медсестра: " + name);
        System.out.println("Медсестра добавлена: " + nurse.getInfo());
    }

    public void listAll() {
        if (people.isEmpty()) {
            System.out.println("Список пуст.");
            logger.warn("Попытка просмотра пустого списка");
            return;
        }
        System.out.println("\n══════════════════════════════════════════════════════════════");
        System.out.println("  СПИСОК ПЕРСОНАЛА И ПАЦИЕНТОВ БОЛЬНИЦЫ");
        System.out.println("══════════════════════════════════════════════════════════════");
        for (int i = 0; i < people.size(); i++) {
            System.out.printf("[%2d] %s%n", i + 1, people.get(i).getInfo());
        }
        System.out.println("══════════════════════════════════════════════════════════════\n");
        logger.info("Просмотр всех записей. Кол-во: " + people.size());
    }

    public void deletePerson(int index) {
        if (index < 0 || index >= people.size()) {
            System.out.println("Неверный номер записи.");
            logger.error("Попытка удалить несуществующую запись: индекс " + index);
            return;
        }
        Person removed = people.remove(index);
        logger.info("Удалена запись: " + removed.getName() + " [" + removed.getRole() + "]");
        System.out.println("Удалена запись: " + removed.getName());
    }

    public void editSpecificField(int index, String newValue) {
        if (index < 0 || index >= people.size()) {
            System.out.println("Неверный номер записи.");
            logger.error("Попытка редактирования несуществующей записи: " + index);
            return;
        }
        Person person = people.get(index);

        if (person instanceof Patient) {
            ((Patient) person).setDiagnosis(newValue);
            System.out.println("Диагноз пациента обновлён: " + newValue);
        } else if (person instanceof Doctor) {
            ((Doctor) person).setSpecialization(newValue);
            System.out.println("Специализация врача обновлена: " + newValue);
        } else if (person instanceof Nurse) {
            ((Nurse) person).setShift(newValue);
            System.out.println("Смена медсестры обновлена: " + newValue);
        }
        logger.info("Отредактирована запись [" + index + "]: " + person.getName() + " → " + newValue);
    }

    public void searchByName(String query) {
        System.out.println("\n── Результаты поиска по имени: \"" + query + "\" ──");
        boolean found = false;

        for (Person p : people) {
            if (p.getName().toLowerCase().contains(query.toLowerCase())) {
                System.out.println(p.getInfo());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Ничего не найдено.");
        }
        logger.info("Поиск по имени: \"" + query + "\"");
    }

    public void filterByRole(String role) {
        System.out.println("\n── Фильтр по роли: " + role + " ──");
        List<Person> filtered = people.stream()
                .filter(p -> p.getRole().equalsIgnoreCase(role))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("Нет записей с ролью: " + role);
        } else {
            filtered.forEach(p -> System.out.println(p.getInfo()));
        }
        logger.info("Фильтр по роли: " + role + " | Найдено: " + filtered.size());
    }

    public void filterByAge(int minAge, int maxAge) {
        System.out.println("\n── Фильтр по возрасту: от " + minAge + " до " + maxAge + " лет ──");
        List<Person> filtered = people.stream()
                .filter(p -> p.getAge() >= minAge && p.getAge() <= maxAge)
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("Нет записей в данном возрастном диапазоне.");
        } else {
            filtered.forEach(p -> System.out.println(p.getInfo()));
        }
        logger.info("Фильтр по возрасту: " + minAge + "–" + maxAge + " | Найдено: " + filtered.size());
    }

    public void filterByDepartment(String department) {
        System.out.println("\n── Фильтр по отделению: " + department + " ──");
        List<Person> filtered = people.stream()
                .filter(p -> p.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("Нет записей в отделении: " + department);
        } else {
            filtered.forEach(p -> System.out.println(p.getInfo()));
        }
        logger.info("Фильтр по отделению: " + department + " | Найдено: " + filtered.size());
    }

    public void sortByName() {
        people.sort(Comparator.comparing(Person::getName));
        System.out.println("Список отсортирован по имени (А→Я).");
        logger.info("Сортировка по имени");
        listAll();
    }

    public void sortByDepartment() {
        people.sort(Comparator.comparing(Person::getDepartment));
        System.out.println("Список отсортирован по отделению.");
        logger.info("Сортировка по отделению");
        listAll();
    }

    public void showStatistics() {
        long doctorCount  = people.stream().filter(p -> p instanceof Doctor).count();
        long patientCount = people.stream().filter(p -> p instanceof Patient).count();
        long nurseCount   = people.stream().filter(p -> p instanceof Nurse).count();

        System.out.println("\n══════════════════════════════════");
        System.out.println("  СТАТИСТИКА ПО РОЛЯМ");
        System.out.println("══════════════════════════════════");
        System.out.println("  Врачи:       " + doctorCount);
        System.out.println("  Пациенты:    " + patientCount);
        System.out.println("  Медсёстры:   " + nurseCount);
        System.out.println("  Всего:       " + people.size());
        System.out.println("══════════════════════════════════\n");
        logger.info("Просмотр статистики");
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Person p : people) {
                writer.write(p.toFileString());
                writer.newLine();
            }
            System.out.println("Данные сохранены в файл: " + FILE_NAME);
            logger.info("Данные сохранены в файл. Записей: " + people.size());
        } catch (IOException e) {
            // Обработка исключения при ошибке записи
            System.out.println("Ошибка сохранения: " + e.getMessage());
            logger.error("Ошибка сохранения в файл: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Файл не найден: " + FILE_NAME);
            logger.warn("Файл для загрузки не найден: " + FILE_NAME);
            return;
        }

        people.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length != 5) continue;

                String type  = parts[0];
                String name  = parts[1];
                int    age   = Integer.parseInt(parts[2]);
                String dept  = parts[3];
                String extra = parts[4];

                switch (type) {
                    case "com.hospital.Doctor":  people.add(new Doctor(name, age, dept, extra));  break;
                    case "com.hospital.Patient": people.add(new Patient(name, age, dept, extra)); break;
                    case "com.hospital.Nurse":   people.add(new Nurse(name, age, dept, extra));   break;
                    default:
                        logger.warn("Неизвестный тип при загрузке: " + type);
                }
            }
            System.out.println("Загружено " + people.size() + " записей из файла: " + FILE_NAME);
            logger.info("Данные загружены. Записей: " + people.size());
        } catch (IOException e) {
            System.out.println("Ошибка загрузки: " + e.getMessage());
            logger.error("Ошибка загрузки из файла: " + e.getMessage());
        }
    }

    public int getCount() {
        return people.size();
    }
}