package accountcreation;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class EasyKanbanMain {
    public static ArrayList<Task> tasks = new ArrayList<>();
    public static ArrayList<String> developers = new ArrayList<>();
    public static ArrayList<String> taskNames = new ArrayList<>();
    public static ArrayList<String> taskIDs = new ArrayList<>();
    public static ArrayList<Integer> taskDurations = new ArrayList<>();
    public static ArrayList<String> taskStatuses = new ArrayList<>();

    public static void main(String[] args) {
        // Add test data
        addTestData();

        try (Scanner scanner = new Scanner(System.in)) {
            // Account creation and login code omitted for brevity
            // Prompt the user to create an account by entering username, password, first name, and last name
            System.out.println("To create an account, please enter the following");

            // First name
            System.out.println("Enter First Name (REQUIRED): ");
            String firstName = scanner.nextLine();

            // Check first name conditions and output messages based on the conditions
            if (firstName.isEmpty()) {
                System.out.println("First name is required, please enter first name");
            } else {
                System.out.println("First name is successfully captured");
            }

            // Last name
            System.out.println("Enter Last Name (REQUIRED): ");
            String lastName = scanner.nextLine();

            // Check last name conditions and output messages based on the conditions
            if (lastName.isEmpty()) {
                System.out.println("Last name is required, please enter last name");
            } else {
                System.out.println("Last name is successfully captured");
            }

            // Username
            System.out.println("Enter Username (MUST contain an underscore and be no more than 5 characters): ");
            String userName = scanner.nextLine();

            // Check username conditions and output messages based on the conditions
            if (isUsernameValid(userName)) {
                System.out.println("Username is successfully captured");
            } else {
                System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length!");
            }

            // Password
            System.out.println("Enter Password (MUST be at least 8 characters, contain a capital letter, a number, and a special character): ");
            String passWord = scanner.nextLine();

            // Check password conditions and output messages based on the conditions
            if (isPasswordValid(passWord)) {
                System.out.println("Password is successfully captured");
            } else {
                System.out.println("Password is not correctly formatted, please ensure that your password contains at least 8 characters, a capital letter, a number, and a special character!");
            }

            // Account Registration
            if (isUsernameValid(userName) && isPasswordValid(passWord)) {
                System.out.println("You have successfully created an account");
            } else {
                System.out.println("You have failed to create an account, please enter valid information!!");
            }

            // Login User Feature
            System.out.print("Please enter the following to Login, ");

            String predefinedUsername = "Mpho_";
            String predefinedPassword = "Qu3st!on";
            String predefinedFirstName = "Mpho";
            String predefinedLastName = "Thabane";

            // Prompt user to input username and password
            System.out.print("Enter username: ");
            String enteredUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String enteredPassword = scanner.nextLine();

            // Compare input with predefined values
            if (enteredUsername.equals(predefinedUsername) && enteredPassword.equals(predefinedPassword)) {
                System.out.println("Welcome, " + predefinedFirstName + " " + predefinedLastName + ", it is great to see you again.");
            } else {
                System.out.println("Username or password incorrect, please try again.");
            }

            // Easy Kanban (Task Functionality features)
            System.out.println("Welcome to Easy Kanban");

            while (true) {
                System.out.println("Option 1) Add tasks");
                System.out.println("Option 2) Show report");
                System.out.println("Option 3) Quit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (option == 3) {
                    break;
                }

                switch (option) {
                    case 1 -> addTasks(scanner);
                    case 2 -> showReport();
                    default -> System.out.println("Invalid option. Please choose again.");
                }
            }
        }
    }

    private static void addTasks(Scanner scanner) {
        System.out.print("Enter number of tasks: ");
        int taskNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < taskNumber; i++) {
            System.out.print("Enter task name: ");
            String taskName = scanner.nextLine();

            System.out.print("Enter task description: ");
            String taskDescription = scanner.nextLine();
            if (taskDescription.length() > 50) {
                System.out.println("Please enter a task description of less than 50 characters");
                i--; // to retry the current task
                continue;
            }

            System.out.print("Enter developer details: ");
            String developerDetails = scanner.nextLine();

            System.out.print("Enter task duration in hours: ");
            int taskDuration = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter task status (To Do, Doing, Done): ");
            String taskStatus = scanner.nextLine();

            Task task = new Task(taskName, taskDescription, developerDetails, taskDuration, taskStatus);
            System.out.println("Task successfully captured");
            tasks.add(task);

            // Add to arrays
            developers.add(developerDetails);
            taskNames.add(taskName);
            taskIDs.add(task.generateTaskID());
            taskDurations.add(taskDuration);
            taskStatuses.add(taskStatus);

            // Display task details
            JOptionPane.showMessageDialog(null, task.printTaskDetails());
        }
    }

    private static void showReport() {
        // a. Display the Developer, Task Names and Task Duration for all tasks with the status of done.
        System.out.println("Tasks with status 'Done':");
        for (int i = 0; i < tasks.size(); i++) {
            if (taskStatuses.get(i).equalsIgnoreCase("Done")) {
                System.out.println("Developer: " + developers.get(i) + ", Task Name: " + taskNames.get(i) + ", Duration: " + taskDurations.get(i));
            }
        }

        // b. Display the Developer and Duration of the task with the longest duration.
        int maxDurationIndex = 0;
        for (int i = 1; i < taskDurations.size(); i++) {
            if (taskDurations.get(i) > taskDurations.get(maxDurationIndex)) {
                maxDurationIndex = i;
            }
        }
        System.out.println("Task with longest duration: Developer: " + developers.get(maxDurationIndex) + ", Duration: " + taskDurations.get(maxDurationIndex));

        // c. Search for a task with a Task Name and display the Task Name, Developer and Task Status.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter task name to search: ");
        String searchTaskName = scanner.nextLine();
        for (int i = 0; i < tasks.size(); i++) {
            if (taskNames.get(i).equalsIgnoreCase(searchTaskName)) {
                System.out.println("Task found: Task Name: " + taskNames.get(i) + ", Developer: " + developers.get(i) + ", Task Status: " + taskStatuses.get(i));
                break;
            }
        }

        // d. Search for all tasks assigned to a developer and display the Task Name and Task Status.
        System.out.print("Enter developer name to search: ");
        String searchDeveloper = scanner.nextLine();
        System.out.println("Tasks assigned to " + searchDeveloper + ":");
        for (int i = 0; i < tasks.size(); i++) {
            if (developers.get(i).equalsIgnoreCase(searchDeveloper)) {
                System.out.println("Task Name: " + taskNames.get(i) + ", Task Status: " + taskStatuses.get(i));
            }
        }

        // e. Delete a task using the Task Name.
        System.out.print("Enter task name to delete: ");
        String deleteTaskName = scanner.nextLine();
        for (int i = 0; i < tasks.size(); i++) {
            if (taskNames.get(i).equalsIgnoreCase(deleteTaskName)) {
                tasks.remove(i);
                developers.remove(i);
                taskNames.remove(i);
                taskIDs.remove(i);
                taskDurations.remove(i);
                taskStatuses.remove(i);
                System.out.println("Task '" + deleteTaskName + "' successfully deleted.");
                break;
            }
        }

        // f. Display a report that lists the full details of all captured tasks.
        System.out.println("Full task report:");
        for (Task task : tasks) {
            System.out.println(task.printTaskDetails());
        }
    }

    private static void addTestData() {
        tasks.add(new Task("Create Login", "Create a login page", "Mike Smith", 5, "To Do"));
        tasks.add(new Task("Create Add features", "Add new features", "Edward Harrison", 8, "Doing"));
        tasks.add(new Task("Create Reports", "Generate reports", "Samantha Paulson", 2, "Done"));
        tasks.add(new Task("Add Arrays", "Implement array functionality", "Glenda Orbeholzer", 11, "To Do"));

        developers.add("Mike Smith");
        developers.add("Edward Harrison");
        developers.add("Samantha Paulson");
        developers.add("Glenda Orbeholzer");

        taskNames.add("Create Login");
        taskNames.add("Create Add features");
        taskNames.add("Create Reports");
        taskNames.add("Add Arrays");

        taskIDs.add("1");
        taskIDs.add("2");
        taskIDs.add("3");
        taskIDs.add("4");

        taskDurations.add(5);
        taskDurations.add(8);
        taskDurations.add(2);
        taskDurations.add(11);

        taskStatuses.add("To Do");
        taskStatuses.add("Doing");
        taskStatuses.add("Done");
        taskStatuses.add("To Do");
    }

    public static boolean isUsernameValid(String userName) {
        return userName.length() <= 5 && userName.contains("_");
    }

    public static boolean isPasswordValid(String passWord) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
        return Pattern.compile(passwordPattern).matcher(passWord).matches();
    }
}

// Task Class Implementation
class Task {
    private static int idCounter = 1;
    private final String taskName;
    private final String taskDescription;
    private final String developerDetails;
    private final int taskDuration;
    private final String taskStatus;
    private final int taskId;

    public Task(String taskName, String taskDescription, String developerDetails, int taskDuration, String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.developerDetails = developerDetails;
        this.taskDuration = taskDuration;
        this.taskStatus = taskStatus;
        this.taskId = idCounter++;
    }

    public int getTaskDuration() {
        return taskDuration;
    }

    public String printTaskDetails() {
        return "Task ID: " + taskId +
               "\nTask Name: " + taskName +
               "\nTask Description: " + taskDescription +
               "\nDeveloper Details: " + developerDetails +
               "\nTask Duration: " + taskDuration + " hours" +
               "\nTask Status: " + taskStatus;
    }

    public String generateTaskID() {
        return String.valueOf(taskId);
    }
}
