package com.hospital;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.util.Scanner;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {

        PropertyConfigurator.configure(Main.class.getClassLoader().getResourceAsStream("log4j.properties"));
        logger.info("═══ Приложение «Больница» запущено ═══");

        HospitalManager manager = new HospitalManager();

        Scanner sc = new Scanner(System.in);

        boolean running = true;

        while (running) {
            printMenu();

            System.out.print("Ваш выбор: ");
            String input = sc.nextLine().trim();

            switch (input) {

                case "1":
                    System.out.print("Имя пациента: ");
                    String pName = sc.nextLine();

                    System.out.print("Возраст: ");
                    int pAge = parseIntSafe(sc.nextLine());

                    System.out.print("Отделение: ");
                    String pDept = sc.nextLine();

                    System.out.print("Диагноз: ");
                    String pDiag = sc.nextLine();

                    manager.addPatient(pName, pAge, pDept, pDiag);
                    break;

                case "2":
                    System.out.print("Имя врача: ");
                    String dName = sc.nextLine();

                    System.out.print("Возраст: ");
                    int dAge = parseIntSafe(sc.nextLine());

                    System.out.print("Отделение: ");
                    String dDept = sc.nextLine();

                    System.out.print("Специализация: ");
                    String dSpec = sc.nextLine();

                    manager.addDoctor(dName, dAge, dDept, dSpec);
                    break;

                case "3":
                    System.out.print("Имя медсестры: ");
                    String nName = sc.nextLine();

                    System.out.print("Возраст: ");
                    int nAge = parseIntSafe(sc.nextLine());

                    System.out.print("Отделение: ");
                    String nDept = sc.nextLine();

                    System.out.print("Смена (Дневная/Ночная/Суточная): ");
                    String nShift = sc.nextLine();

                    manager.addNurse(nName, nAge, nDept, nShift);
                    break;

                case "4":
                    manager.listAll();
                    break;

                case "5":
                    if (!checkAdmin(sc)) break;
                    manager.listAll();
                    System.out.print("Введите номер записи для удаления: ");
                    int delIndex = parseIntSafe(sc.nextLine()) - 1;
                    manager.deletePerson(delIndex);
                    break;

                case "6":
                    if (!checkAdmin(sc)) break;
                    manager.listAll();
                    System.out.print("Введите номер записи для редактирования: ");
                    int editIndex = parseIntSafe(sc.nextLine()) - 1;

                    System.out.print("Новое значение (диагноз / специализация / смена): ");
                    String newVal = sc.nextLine();

                    manager.editSpecificField(editIndex, newVal);
                    break;

                case "7":
                    System.out.print("Введите имя (или часть имени) для поиска: ");
                    String query = sc.nextLine();
                    manager.searchByName(query);
                    break;

                case "8":
                    System.out.println("Роли: com.hospital.Doctor | com.hospital.Patient | com.hospital.Nurse");
                    System.out.print("Введите роль: ");
                    String role = sc.nextLine();
                    manager.filterByRole(role);
                    break;

                case "9":
                    System.out.print("Минимальный возраст: ");
                    int minAge = parseIntSafe(sc.nextLine());
                    System.out.print("Максимальный возраст: ");
                    int maxAge = parseIntSafe(sc.nextLine());
                    manager.filterByAge(minAge, maxAge);
                    break;

                case "10":
                    System.out.print("Введите название отделения: ");
                    String dept = sc.nextLine();
                    manager.filterByDepartment(dept);
                    break;

                case "11":
                    manager.sortByName();
                    break;

                case "12":
                    manager.sortByDepartment();
                    break;

                case "13":
                    manager.showStatistics();
                    break;

                case "14":
                    manager.saveToFile();
                    break;

                case "15":
                    manager.loadFromFile();
                    break;

                case "0":
                    System.out.println("До свидания!");
                    logger.info("═══ Приложение завершено ═══");
                    running = false;
                    break;

                default:
                    System.out.println("Неверный пункт меню. Попробуйте ещё раз.");
                    logger.warn("Неверный ввод в меню: " + input);
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║        БОЛЬНИЦА — МЕНЮ           ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║  1.  Добавить пациента           ║");
        System.out.println("║  2.  Добавить врача              ║");
        System.out.println("║  3.  Добавить медсестру          ║");
        System.out.println("║  4.  Просмотреть всех            ║");
        System.out.println("║  5.  Удалить запись [АДМИН]      ║");
        System.out.println("║  6.  Редактировать запись [АДМИН]║");
        System.out.println("║  7.  Поиск по имени              ║");
        System.out.println("║  8.  Фильтр по роли              ║");
        System.out.println("║  9.  Фильтр по возрасту          ║");
        System.out.println("║  10. Фильтр по отделению         ║");
        System.out.println("║  11. Сортировка по имени         ║");
        System.out.println("║  12. Сортировка по отделению     ║");
        System.out.println("║  13. Статистика по ролям         ║");
        System.out.println("║  14. Сохранить данные            ║");
        System.out.println("║  15. Загрузить данные            ║");
        System.out.println("║  0.  Выход                       ║");
        System.out.println("╚══════════════════════════════════╝");
    }

    private static boolean checkAdmin(Scanner sc) {
        System.out.print("Введите пароль администратора: ");
        String password = sc.nextLine();
        if (ADMIN_PASSWORD.equals(password)) {
            logger.info("Успешный вход администратора");
            return true;
        } else {
            System.out.println("Неверный пароль. Доступ запрещён.");
            logger.warn("Неверная попытка входа администратора");
            return false;
        }
    }

    private static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: введите корректное число. Используется значение -1.");
            logger.warn("Ошибка парсинга числа: \"" + s + "\"");
            return -1; // Возвращаем -1 как признак ошибки
        }
    }
}