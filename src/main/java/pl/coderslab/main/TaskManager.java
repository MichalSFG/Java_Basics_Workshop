package pl.coderslab.main;

import pl.coderslab.utils.ConsoleColors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String[][] tasks = getTasksFromFile();
        options();
        String optionChosenByUser = sc.nextLine();
        switch (optionChosenByUser) {
            case "add":
                addTask(tasks);
                break;
        }
    }

    private static void addTask(String[][] tasks) {
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

    private static void printTasks(String[][] tasks) {
        for (String[] task : tasks) {
            for (String s : task) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    private static String[][] getTasksFromFile() {
        String[][] tasks = new String[0][];
        int index = 0;
        String fileName = "tasks.csv";
        File file = new File(fileName);
        try (Scanner scanner = new Scanner(file)){
            while (scanner.hasNextLine()) {
                String task = scanner.nextLine();
                String[] split = task.split(" ");
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[index] = split;
                index++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Nie znaleziono pliku " + fileName);
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
