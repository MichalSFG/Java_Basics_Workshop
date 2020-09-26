package pl.coderslab.main;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import pl.coderslab.utils.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    private static Scanner sc = new Scanner(System.in);
    private static String[][] tasks = getTasksFromFile();
    private static final String FILE = "tasks.csv";
    private static final String EXIT = "exit";

    public static void main(String[] args) {

        String optionChosenByUser;
        do {
            options();
            optionChosenByUser = sc.nextLine();
            switch (optionChosenByUser) {
                case "add":
                    addTask();
                    break;
                case "list":
                    printTasks();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "exit":
                    saveToFile();
                    break;
                default:
                    System.out.println("Please select a correct option!");
            }
        } while (!optionChosenByUser.equals(EXIT));
    }

    private static void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE)) {
            for (String[] task : tasks) {
                for (int j = 0; j < task.length; j++) {
                    if (j != task.length - 1) {
                        writer.append(task[j]).append(", ");
                    } else {
                        writer.append(task[j]);
                    }
                }
                writer.append("\n");
            }
            System.out.println(ConsoleColors.RED + "Bye,bye");
        } catch (IOException e) {
            System.err.println("Błąd zapisu danych do pliku!");
        }
    }

    private static void removeTask() {
        System.out.println("Please select number to remove.");
        while (true) {
            String s = sc.nextLine();
            if (NumberUtils.isParsable(s)) {
                int num = Integer.parseInt(s);
                if (num >= 0 && num < tasks.length) {
                    tasks = ArrayUtils.remove(tasks, num);
                    System.out.println("Value was successfully deleted.");
                    break;
                } else
                    System.out.println("Input number from 0 to " + (tasks.length - 1));
            } else
                System.out.println("Incorrect argument passed. Please give number greater or equal 0");

        }
    }

    private static void addTask() {
        System.out.println("Please add task description");
        String desc = sc.nextLine();
        System.out.println("Please add task due date");
        String date = sc.nextLine();
        System.out.println("Is your task important?: true/false");
        String importance = sc.nextLine();
        String[] task = {desc, date, importance};
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = task;
    }

    private static void printTasks() {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static String[][] getTasksFromFile() {
        String[][] tasks = new String[0][];
        int index = 0;
        File file = new File(FILE);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String task = scanner.nextLine();
                String[] split = task.split(", ");
                for (int i = 0; i < split.length; i++) {
                    if (split[i].contains(",")) {
                        String modifiedElement = split[i].substring(0, split[i].length() - 1);
                        split[i] = modifiedElement;
                    }
                }
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[index] = split;
                index++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku " + FILE);
        }
        return tasks;
    }

    private static void options() {
        String[] strings = new String[5];
        strings[0] = ConsoleColors.BLUE + "Please select an option:";
        strings[1] = ConsoleColors.RESET + "add";
        strings[2] = "remove";
        strings[3] = "list";
        strings[4] = "exit";
        for (String s : strings) {
            System.out.println(s);
        }
    }
}
