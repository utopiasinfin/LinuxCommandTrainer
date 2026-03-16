package trainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LinuxCommandTrainer {

    static class InputClosedException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }

    // --- ANSI color codes ---
    static final String RESET  = "\u001B[0m";
    static final String BLUE   = "\u001B[34m";
    static final String GREEN  = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String PURPLE = "\u001B[35m";
    static final String RED    = "\u001B[31m";
    static final String CYAN   = "\u001B[36m";
    static final String BOLD   = "\u001B[1m";

    static final Scanner scanner = new Scanner(System.in);

    // Show "Press ENTER" hint only on first pause
    static boolean firstPause = true;

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    static void printSlow(String text, int delayMillis) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            if (delayMillis > 0) {
                try { Thread.sleep(delayMillis); } catch (InterruptedException ignored) {}
            }
        }
        System.out.println();
    }

    static void pause() {
        if (firstPause) {
            System.out.print(CYAN + "  [Press ENTER to continue...]" + RESET);
            firstPause = false;
        }
        readLine();
    }

    static String readLine() {
        if (!scanner.hasNextLine()) {
            throw new InputClosedException();
        }
        return scanner.nextLine();
    }

    static boolean isBackInput(String input) {
        String trimmed = input.trim();
        return trimmed.equalsIgnoreCase("q") || trimmed.equals("\u001B");
    }

    static void printBackHint(String text) {
        System.out.println(CYAN + "  " + text + RESET);
    }

    static boolean waitForEnterOrBack(String prompt) {
        System.out.print(CYAN + prompt + RESET);
        return isBackInput(readLine());
    }

    static void exitProgram() {
        System.out.print(CYAN);
        printSlow("\n  Thanks for learning Linux! Keep exploring!", 15);
        System.out.println(RESET);
        scanner.close();
    }

    static void clearConsole() {
        for (int i = 0; i < 50; i++) System.out.println();
    }

    static void hr(String color) {
        System.out.println(color + "─────────────────────────────────────────────────" + RESET);
    }

    // -------------------------------------------------------------------------
    // Intro
    // -------------------------------------------------------------------------

    static void intro() {
        System.out.print(BLUE);
        printSlow(
            "      .--.\n" +
            "     |o_o |\n" +
            "     |:_/ |\n" +
            "    //   \\\\ \\\\\n" +
            "   (|     | )\n" +
            "  /'\\_   _/`\\\\\n" +
            "  \\___)=(___/\n", 8);
        System.out.print(RESET);
        pause();

        System.out.print(GREEN);
        printSlow("  !! Welcome to the Linux Command Trainer !!", 15);
        System.out.print(RESET);
        pause();

        System.out.print(YELLOW);
        printSlow("  Celebrating freedom, collaboration, and the power of open source.", 12);
        System.out.print(RESET);
        pause();
    }

    // -------------------------------------------------------------------------
    // Language selection
    // -------------------------------------------------------------------------

    static Language chooseLanguage() {
        System.out.println();
        hr(PURPLE);
        System.out.println(PURPLE + BOLD + "  Choose language / Sprache wählen:" + RESET);
        hr(PURPLE);
        System.out.println("  [1] English");
        System.out.println("  [2] Deutsch");
        System.out.println("  [3] Both / Beide (EN + DE)");
        printBackHint("Type q or press Esc, then ENTER, to exit.");
        System.out.println();
        while (true) {
            System.out.print("  Enter 1, 2, or 3: ");
                String choice = readLine().trim();
            if (isBackInput(choice)) return null;
            switch (choice) {
                case "1": return Language.ENGLISH;
                case "2": return Language.GERMAN;
                case "3": return Language.BOTH;
                default:
                    System.out.println(RED + "  Invalid input. Please enter 1, 2, or 3, or q to exit." + RESET);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Distro selection
    // -------------------------------------------------------------------------

    static String chooseDistro() {
        System.out.println();
        hr(PURPLE);
        System.out.println(PURPLE + BOLD + "  Choose your Linux distribution:" + RESET);
        hr(PURPLE);
        System.out.println("  [1] Debian / Ubuntu");
        System.out.println("  [2] Arch / Manjaro");
        System.out.println("  [3] Fedora");
        printBackHint("Type q or press Esc, then ENTER, to go back.");
        System.out.println();
        while (true) {
            System.out.print("  Enter 1, 2, or 3: ");
                String choice = readLine().trim();
            if (isBackInput(choice)) return null;
            switch (choice) {
                case "1": return "Debian/Ubuntu";
                case "2": return "Arch/Manjaro";
                case "3": return "Fedora";
                default:
                    System.out.println(RED + "  Invalid input. Please enter 1, 2, or 3, or q to go back." + RESET);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Difficulty selection
    // -------------------------------------------------------------------------

    static Difficulty chooseLevel() {
        System.out.println();
        hr(PURPLE);
        System.out.println(PURPLE + BOLD + "  Choose your difficulty level:" + RESET);
        hr(PURPLE);
        System.out.println("  [1] Beginner     -- Core navigation and file management");
        System.out.println("  [2] Intermediate -- Text processing, archives, and permissions");
        System.out.println("  [3] Advanced     -- Processes, networking, and system management");
        printBackHint("Type q or press Esc, then ENTER, to go back.");
        System.out.println();
        while (true) {
            System.out.print("  Enter 1, 2, or 3: ");
                String choice = readLine().trim();
            if (isBackInput(choice)) return null;
            switch (choice) {
                case "1": return Difficulty.BEGINNER;
                case "2": return Difficulty.INTERMEDIATE;
                case "3": return Difficulty.ADVANCED;
                default:
                    System.out.println(RED + "  Invalid input. Please enter 1, 2, or 3, or q to go back." + RESET);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Mode menu
    // -------------------------------------------------------------------------

    static Integer chooseModeMenu(Language lang, Difficulty level) {
        System.out.println();
        hr(CYAN);
        String langLabel = lang == Language.ENGLISH ? "English" :
                           lang == Language.GERMAN  ? "Deutsch" : "EN+DE";
        System.out.println(CYAN + BOLD + "  Mode Menu  [" + level + " | " + langLabel + "]" + RESET);
        hr(CYAN);
        System.out.println("  [1] Show command list");
        System.out.println("  [2] Learn mode  (detailed explanations)");
        System.out.println("  [3] Command quiz  (type the command from the description)");
        System.out.println("  [4] Flags quiz  (multiple choice)");
        System.out.println("  [5] Change language / distro / level");
        System.out.println("  [6] Exit");
        printBackHint("Type q or press Esc, then ENTER, to go back to difficulty selection.");
        System.out.println();
        while (true) {
            System.out.print("  Your choice: ");
                String choice = readLine().trim();
            if (isBackInput(choice)) return null;
            switch (choice) {
                case "1": return 1;
                case "2": return 2;
                case "3": return 3;
                case "4": return 4;
                case "5": return 5;
                case "6": return 6;
                default:
                    System.out.println(RED + "  Invalid choice. Enter 1-6, or q to go back." + RESET);
            }
        }
    }

    // -------------------------------------------------------------------------
    // Mode 1 -- Show command list
    // -------------------------------------------------------------------------

    static void showCommands(List<LinuxCommand> commands, Language lang) {
        clearConsole();
        System.out.println();
        System.out.println(BLUE + BOLD + "  -- Command List --" + RESET);
        for (LinuxCommand cmd : commands) {
            System.out.println();
            System.out.print(CYAN + BOLD + "  " + cmd.getCommand() + RESET);
            if (cmd.getSyntax() != null && !cmd.getSyntax().isEmpty()) {
                System.out.print(YELLOW + "  ->  " + cmd.getSyntax() + RESET);
            }
            System.out.println();
            if (lang == Language.ENGLISH || lang == Language.BOTH) {
                System.out.println("    " + cmd.getExplainEnShort());
            }
            if (lang == Language.GERMAN || lang == Language.BOTH) {
                System.out.println("    [DE] " + cmd.getExplainDeShort());
            }
            if (!cmd.getExamples().isEmpty()) {
                LinuxCommand.Example ex = cmd.getExamples().get(0);
                System.out.print(GREEN + "    $ " + ex.cmd + RESET);
                if (lang == Language.ENGLISH || lang == Language.BOTH) {
                    System.out.print("  -- " + ex.en);
                }
                if (lang == Language.GERMAN) {
                    System.out.print("  -- " + ex.de);
                } else if (lang == Language.BOTH) {
                    System.out.print(" / " + ex.de);
                }
                System.out.println();
            }
        }
        System.out.println();
        waitForEnterOrBack("  [Press ENTER, q, or Esc+ENTER to return to the menu...]");
    }

    // -------------------------------------------------------------------------
    // Mode 2 -- Learn mode
    // -------------------------------------------------------------------------

    static void learnMode(List<LinuxCommand> commands, Language lang) {
        clearConsole();
        System.out.println(GREEN + BOLD + "\n  -- Learn Mode --" + RESET);
        System.out.println(YELLOW + "  Go through each command with full details.\n" + RESET);
        printBackHint("Press q or Esc+ENTER during a pause to return to the menu.");
        if (waitForEnterOrBack("  [Press ENTER to begin, q or Esc+ENTER to return to the menu... ]")) {
            System.out.println(CYAN + "\n  Returning to the menu..." + RESET);
            return;
        }

        int idx = 1;
        for (LinuxCommand cmd : commands) {
            clearConsole();
            System.out.println(PURPLE + BOLD + "\n  [" + idx + "/" + commands.size() + "]  " + cmd.getCommand() + RESET);
            if (cmd.getSyntax() != null && !cmd.getSyntax().isEmpty()) {
                System.out.println(YELLOW + "  Syntax: " + cmd.getSyntax() + RESET);
            }
            hr(BLUE);

            if (lang == Language.ENGLISH || lang == Language.BOTH) {
                System.out.println(CYAN + "  [EN] " + RESET + cmd.getExplainEnLong());
            }
            if (lang == Language.GERMAN || lang == Language.BOTH) {
                System.out.println(CYAN + "  [DE] " + RESET + cmd.getExplainDeLong());
            }

            if (!cmd.getFlags().isEmpty()) {
                System.out.println(YELLOW + "\n  Flags / Options:" + RESET);
                for (LinuxCommand.Flag f : cmd.getFlags()) {
                    System.out.print(GREEN + "    " + f.flag + RESET + "  --  ");
                    if (lang == Language.ENGLISH) {
                        System.out.println(f.en);
                    } else if (lang == Language.GERMAN) {
                        System.out.println(f.de);
                    } else {
                        System.out.println(f.en + " / " + f.de);
                    }
                }
            }

            if (!cmd.getExamples().isEmpty()) {
                System.out.println(YELLOW + "\n  Examples:" + RESET);
                for (LinuxCommand.Example ex : cmd.getExamples()) {
                    System.out.println(GREEN + "    $ " + ex.cmd + RESET);
                    if (lang == Language.ENGLISH || lang == Language.BOTH) {
                        System.out.println("      -> " + ex.en);
                    }
                    if (lang == Language.GERMAN || lang == Language.BOTH) {
                        System.out.println("      -> [DE] " + ex.de);
                    }
                }
            }

            if (!cmd.getPitfalls().isEmpty()) {
                System.out.println(RED + "\n  /!\\ Safety Notes:" + RESET);
                for (LinuxCommand.Pitfall p : cmd.getPitfalls()) {
                    if (lang == Language.ENGLISH || lang == Language.BOTH) {
                        System.out.println("    " + p.en);
                    }
                    if (lang == Language.GERMAN || lang == Language.BOTH) {
                        System.out.println("    [DE] " + p.de);
                    }
                }
            }

            System.out.println();
            hr(BLUE);
            if (waitForEnterOrBack("  [Press ENTER for the next command, q or Esc+ENTER to return to the menu... ]")) {
                System.out.println(CYAN + "\n  Returning to the menu..." + RESET);
                return;
            }
            idx++;
        }

        System.out.println(GREEN + "\n  Learn session complete!" + RESET);
        pause();
    }

    // -------------------------------------------------------------------------
    // Mode 3 -- Command quiz
    // -------------------------------------------------------------------------

    static void commandQuiz(List<LinuxCommand> commands, Language lang) {
        clearConsole();
        List<LinuxCommand> shuffled = new ArrayList<>(commands);
        Collections.shuffle(shuffled);
        int correct = 0;

        System.out.println(PURPLE + BOLD + "\n  -- Command Quiz --" + RESET);
        System.out.println(YELLOW + "  Read the description and type the command.\n" + RESET);
        printBackHint("Type q or press Esc, then ENTER, to return to the menu.");

        for (int i = 0; i < shuffled.size(); i++) {
            LinuxCommand cmd = shuffled.get(i);
            System.out.println(CYAN + "\n  [" + (i + 1) + "/" + shuffled.size() + "]" + RESET);
            if (lang == Language.ENGLISH) {
                System.out.println("  " + cmd.getExplainEnShort());
            } else if (lang == Language.GERMAN) {
                System.out.println("  " + cmd.getExplainDeShort());
            } else {
                System.out.println("  " + cmd.getExplainEnShort());
                System.out.println("  [DE] " + cmd.getExplainDeShort());
            }

            System.out.print(YELLOW + "  Command: " + RESET);
                String input = readLine();
            if (isBackInput(input)) {
                System.out.println(CYAN + "\n  Returning to the menu..." + RESET);
                return;
            }

            if (cmd.matches(input)) {
                System.out.println(GREEN + "  Correct!" + RESET);
                correct++;
            } else {
                System.out.println(RED + "  Wrong. Correct answer: " + CYAN + cmd.getCommand() + RESET);
                if (!cmd.getExamples().isEmpty()) {
                    System.out.println("    Example: " + GREEN + cmd.getExamples().get(0).cmd + RESET);
                }
            }
        }

        System.out.println();
        hr(CYAN);
        System.out.print(CYAN + BOLD);
        printSlow("  Quiz done! Score: " + correct + " / " + shuffled.size(), 8);
        System.out.print(RESET + GREEN);
        printSlow("  Keep learning -- every command you know saves you time!", 10);
        System.out.println(RESET);
        pause();
    }

    // -------------------------------------------------------------------------
    // Mode 4 -- Flags quiz (multiple choice)
    // -------------------------------------------------------------------------

    static void flagsQuiz(List<LinuxCommand> commands, Language lang) {
        clearConsole();

        List<LinuxCommand> cmdPool = new ArrayList<>();
        List<LinuxCommand.Flag> flagPool = new ArrayList<>();
        for (LinuxCommand cmd : commands) {
            for (LinuxCommand.Flag flag : cmd.getFlags()) {
                cmdPool.add(cmd);
                flagPool.add(flag);
            }
        }

        if (flagPool.size() < 2) {
            System.out.println(YELLOW + "\n  Not enough flags to run a quiz for this level.\n" + RESET);
            pause();
            return;
        }

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < flagPool.size(); i++) indices.add(i);
        Collections.shuffle(indices);

        int correct = 0;
        int total = Math.min(indices.size(), 10);

        System.out.println(PURPLE + BOLD + "\n  -- Flags Quiz (Multiple Choice) --" + RESET);
        System.out.println(YELLOW + "  Choose the correct meaning of each flag.\n" + RESET);
        printBackHint("Type q or press Esc, then ENTER, to return to the menu.");

        for (int q = 0; q < total; q++) {
            int idx = indices.get(q);
            LinuxCommand qCmd = cmdPool.get(idx);
            LinuxCommand.Flag qFlag = flagPool.get(idx);

            String correctAnswer = (lang == Language.GERMAN) ? qFlag.de : qFlag.en;

            List<String> wrong = new ArrayList<>();
            List<Integer> shuffledAll = new ArrayList<>(indices);
            Collections.shuffle(shuffledAll);
            for (int wi : shuffledAll) {
                if (wi == idx) continue;
                String candidate = (lang == Language.GERMAN) ? flagPool.get(wi).de : flagPool.get(wi).en;
                if (!candidate.equals(correctAnswer) && !wrong.contains(candidate)) {
                    wrong.add(candidate);
                }
                if (wrong.size() == 3) break;
            }

            String[] fillers = {
                lang == Language.GERMAN ? "Keine besondere Funktion." : "No special function.",
                lang == Language.GERMAN ? "Zeigt die Versionsnummer." : "Show version number.",
                lang == Language.GERMAN ? "Gibt Hilfe aus." : "Display help text."
            };
            int fi = 0;
            while (wrong.size() < 3 && fi < fillers.length) {
                if (!wrong.contains(fillers[fi]) && !fillers[fi].equals(correctAnswer)) {
                    wrong.add(fillers[fi]);
                }
                fi++;
            }

            List<String> options = new ArrayList<>(wrong.subList(0, Math.min(3, wrong.size())));
            int correctPos = (int)(Math.random() * (options.size() + 1));
            options.add(correctPos, correctAnswer);

            System.out.println(CYAN + "\n  [" + (q + 1) + "/" + total + "]  Command: "
                + BOLD + qCmd.getCommand() + RESET
                + CYAN + "   Flag: " + BOLD + qFlag.flag + RESET);
            System.out.println(YELLOW + "  What does this flag do?" + RESET);
            for (int o = 0; o < options.size(); o++) {
                System.out.println("  [" + (o + 1) + "] " + options.get(o));
            }
            System.out.println();

            int userChoice = -1;
            while (userChoice < 1 || userChoice > options.size()) {
                System.out.print("  Your answer (1-" + options.size() + "): ");
                String input = readLine().trim();
                if (isBackInput(input)) {
                    System.out.println(CYAN + "\n  Returning to the menu..." + RESET);
                    return;
                }
                try {
                    userChoice = Integer.parseInt(input);
                    if (userChoice < 1 || userChoice > options.size()) {
                        System.out.println(RED + "  Please enter a number between 1 and " + options.size() + "." + RESET);
                        userChoice = -1;
                    }
                } catch (NumberFormatException e) {
                    System.out.println(RED + "  Please enter a number." + RESET);
                }
            }

            if (options.get(userChoice - 1).equals(correctAnswer)) {
                System.out.println(GREEN + "  Correct!" + RESET);
                correct++;
            } else {
                System.out.println(RED + "  Wrong. Correct answer: " + RESET + correctAnswer);
            }
        }

        System.out.println();
        hr(CYAN);
        System.out.print(CYAN + BOLD);
        printSlow("  Flags quiz done! Score: " + correct + " / " + total, 8);
        System.out.print(RESET + GREEN);
        printSlow("  Knowing your flags makes you a true Linux power user!", 10);
        System.out.println(RESET);
        pause();
    }

    // -------------------------------------------------------------------------
    // Main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        try {
            intro();

            while (true) {
                Language lang = chooseLanguage();
                if (lang == null) {
                    exitProgram();
                    return;
                }

                boolean restartSetup = false;
                while (!restartSetup) {
                    String distro = chooseDistro();
                    if (distro == null) break;

                    while (true) {
                        Difficulty level = chooseLevel();
                        if (level == null) break;

                        List<LinuxCommand> commands = LinuxCommandsDatabase.getCommandsForDistro(distro, level);

                        if (commands.isEmpty()) {
                            System.out.println(RED + "\n  No commands found for this selection. Returning to difficulty selection.\n" + RESET);
                            continue;
                        }

                        System.out.println(GREEN + "\n  Loaded " + commands.size() + " commands." + RESET);

                        boolean chooseAnotherLevel = false;
                        while (!chooseAnotherLevel) {
                            Integer mode = chooseModeMenu(lang, level);
                            if (mode == null) {
                                chooseAnotherLevel = true;
                                break;
                            }

                            switch (mode) {
                                case 1: showCommands(commands, lang); break;
                                case 2: learnMode(commands, lang);    break;
                                case 3: commandQuiz(commands, lang);  break;
                                case 4: flagsQuiz(commands, lang);    break;
                                case 5:
                                    restartSetup = true;
                                    chooseAnotherLevel = true;
                                    break;
                                case 6:
                                    exitProgram();
                                    return;
                                default:
                                    break;
                            }
                        }

                        if (restartSetup) {
                            break;
                        }
                    }
                }
            }
        } catch (InputClosedException e) {
            exitProgram();
        }
    }
}
