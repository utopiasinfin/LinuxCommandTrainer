package trainer;

import java.util.*;

public class LinuxCommandTrainer {

    // --- Colores ANSI para resaltar texto en consola ---
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static final Scanner scanner = new Scanner(System.in);

    // Bandera: solo mostrar "Press ENTER" una vez
    static boolean showPrompt = true;

    // Imprime texto lentamente (efecto máquina de escribir)
    public static void printSlow(String text, int delayMillis) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try { Thread.sleep(delayMillis); } catch (InterruptedException e) {}
        }
        System.out.println();
    }

    // Espera ENTER; muestra mensaje solo la primera vez
    public static void pause() {
        if (showPrompt) {
            System.out.print(ANSI_CYAN + "Press ENTER to continue..." + ANSI_RESET);
            scanner.nextLine();
            showPrompt = false;
        } else {
            scanner.nextLine(); // solo pausa
        }
    }

    // Limpia la consola imprimiendo muchas líneas vacías
    public static void clearConsole() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    // Intro con Tux y bienvenida
    public static void intro() {
        System.out.print(ANSI_BLUE);
        printSlow(
            "      .--.\n" +
            "     |o_o |\n" +
            "     |:_/ |\n" +
            "    //   \\ \\\n" +
            "   (|     | )\n" +
            "  /'\\_   _/`\\\n" +            
            "  \\___)=(___/\n", 10);
        System.out.print(ANSI_RESET);
        pause();

        System.out.print(ANSI_GREEN);
        printSlow("!!Welcome to the Linux Command Trainer!!", 20);
        System.out.print(ANSI_RESET);
        pause();

        System.out.print(ANSI_YELLOW);
        printSlow("Here, we celebrate freedom, collaboration, and the power of open source.", 20);
        System.out.print(ANSI_RESET);
        pause();
    }

    public static String chooseDistro() {
        System.out.print(ANSI_PURPLE);
        printSlow("Choose your Linux distribution:", 15);
        System.out.print(ANSI_RESET);

        System.out.println("[1] Debian/Ubuntu");
        System.out.println("[2] Arch/Manjaro");
        System.out.println("[3] Fedora");

        while (true) {
            System.out.print("Enter 1, 2, or 3: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": return "Debian/Ubuntu";
                case "2": return "Arch/Manjaro";
                case "3": return "Fedora";
                default:
                    System.out.println(ANSI_RED + "Invalid input." + ANSI_RESET);
            }
        }
    }

    public static Difficulty chooseLevel() {
        System.out.print(ANSI_PURPLE);
        printSlow("Choose your difficulty level:", 15);
        System.out.print(ANSI_RESET);

        System.out.println("[1] Beginner");
        System.out.println("[2] Intermediate");
        System.out.println("[3] Advanced");

        while (true) {
            System.out.print("Enter 1, 2, or 3: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": return Difficulty.BEGINNER;
                case "2": return Difficulty.INTERMEDIATE;
                case "3": return Difficulty.ADVANCED;
                default:
                    System.out.println(ANSI_RED + "Invalid input." + ANSI_RESET);
            }
        }
    }

    public static void showCommands(List<LinuxCommand> commands) {
        System.out.println(ANSI_BLUE + "\nAvailable commands:\n" + ANSI_RESET);
        for (LinuxCommand cmd : commands) {
            System.out.println(
                ANSI_CYAN + cmd.getCommand() + ANSI_RESET + " - " +
                ANSI_YELLOW + cmd.getDescription() + ANSI_RESET +
                (cmd.getExample() != null ? "\n    Example: " + cmd.getExample() : ""));
        }
        System.out.println();
    }

    public static void quiz(List<LinuxCommand> commands) {
        Collections.shuffle(commands);
        int correct = 0;

        System.out.println(ANSI_PURPLE + "\nLet’s start the quiz!\n" + ANSI_RESET);

        for (LinuxCommand cmd : commands) {
            System.out.print(ANSI_YELLOW + cmd.getDescription() + ANSI_RESET + "\nCommand: ");
            String input = scanner.nextLine().trim();

            if (cmd.matches(input)) {
                System.out.println(ANSI_GREEN + "Correct!" + ANSI_RESET);
                correct++;
            } else {
                System.out.println(ANSI_RED + "Wrong. Correct command: " + cmd.getCommand() + ANSI_RESET);
            }
        }

        System.out.print(ANSI_CYAN);
        printSlow("\nQuiz finished! You got " + correct + " out of " + commands.size() + " correct.", 10);
        System.out.print(ANSI_RESET);
        System.out.print(ANSI_GREEN);
        printSlow("Thank you for trying to understand this wonderful world of Linux. !Don´t Worry , be Happy!", 30);
        System.out.print(ANSI_RESET);

    }

    public static boolean postQuizMenu() {
        System.out.println("\nWhat do you want to do next?");
        System.out.println("[1] Repeat command list");
        System.out.println("[2] Try the quiz again");
        System.out.println("[3] Change level or distro");
        System.out.println("[4] Exit (write 'ende')");

        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("4") || input.equals("ende")) {
            System.out.println(ANSI_CYAN + "Thanks for learning Linux with us!" + ANSI_RESET);
            return false;
        }
        if (input.equals("1")) return true;
        if (input.equals("2")) return true;
        if (input.equals("3")) {
            main(null);
            return false;
        }
        System.out.println("Invalid choice.");
        return postQuizMenu();
    }

    public static void main(String[] args) {
        intro();

        String distro = chooseDistro();
        Difficulty level = chooseLevel();

        List<LinuxCommand> commands = LinuxCommandsDatabase.getCommandsForDistro(distro, level);

        showCommands(commands);

        System.out.print("Do you want to try a quiz now? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("y")) {
            clearConsole();  // ← ahora sí limpia antes del test
            quiz(commands);
        }

        while (true) {
            if (!postQuizMenu()) break;

            showCommands(commands);
            System.out.print("Do you want to try the quiz now? (y/n): ");
            answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("y")) {
                clearConsole();  // también limpia antes de cada repetición
                quiz(commands);
            }
        }

        scanner.close();
    }
}
