package trainer;

import java.util.*;

public class LinuxHangman {  // the hangman Linux code, was made by myself following the youtubechannel from sergiecode!
							// and then i adapted it to the linux trainer!!!	

    public static final String ANSI_RESET = "\u001b[0m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_YELLOW = "\u001b[33m";

    static Scanner scanner = new Scanner(System.in);

    static Map<String, String> concepts = new LinkedHashMap<>();

    static {
        concepts.put("kernel", "The Kernel is the core of the operating system, managing hardware and system processes.");
        concepts.put("bash", "Bash is the default command-line shell in many Linux distributions, used for scripting and navigation.");
        concepts.put("sudo", "The `sudo` command allows a permitted user to execute a command as the superuser or another user.");
        concepts.put("root", "`root` is the administrative superuser in Unix-like systems with full control over the system.");
        concepts.put("fstab", "The `/etc/fstab` file contains static information about the filesystems and how they should be mounted.");
        concepts.put("systemd", "`systemd` is a system and service manager used to bootstrap the user space and manage services.");
        concepts.put("cron", "`cron` is a time-based job scheduler to automate tasks at fixed times or intervals.");
        concepts.put("grep", "`grep` is a powerful tool used to search text using patterns or regular expressions.");
    }

    public static void play() {
        printSlow(ANSI_CYAN + "\nWelcome to the Linux Hangman!\n" + ANSI_RESET, 20);
        printSlow(ANSI_PURPLE + "Guess the letters of important Linux concepts.\nType 'q' anytime to return to the main menu.\n" + ANSI_RESET, 20);

        List<String> keys = new ArrayList<>(concepts.keySet());
        Collections.shuffle(keys);
        for (String word : keys) {
            if (!playSingleWord(word)) {
                printSlow(ANSI_RED + "Exiting Linux Hangman...\n" + ANSI_RESET, 20);
                return;
            }
        }

        printSlow(ANSI_GREEN + "You’ve completed all the words! Great job learning Linux!\n" + ANSI_RESET, 20);
    }

    private static boolean playSingleWord(String word) {
        Set<Character> guessedLetters = new HashSet<>();
        int attemptsLeft = 6;

        while (attemptsLeft > 0) {
            String masked = maskWord(word, guessedLetters);
            printSlow("\nWord: " + masked, 10);

            if (!masked.contains("_")) {
                printSlow(ANSI_GREEN + "\nCorrect! The word is: " + word + ANSI_RESET, 20);
                printExplanation(word);
                return true;
            }

            System.out.print("Guess a letter (or 'q' to quit): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("q")) return false;
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Please enter a single letter.");
                continue;
            }

            char guess = input.charAt(0);
            if (guessedLetters.contains(guess)) {
                System.out.println("You already guessed that letter.");
                continue;
            }

            guessedLetters.add(guess);

            if (!word.contains(String.valueOf(guess))) {
                attemptsLeft--;
                System.out.println("Wrong! Attempts left: " + attemptsLeft);
            }
        }

        printSlow(ANSI_RED + "\nOut of attempts. The word was: " + word + ANSI_RESET, 20);
        printExplanation(word);
        return true;
    }

    private static String maskWord(String word, Set<Character> guessedLetters) {
        StringBuilder masked = new StringBuilder();
        for (char c : word.toCharArray()) {
            masked.append(guessedLetters.contains(c) ? c : "_");
        }
        return masked.toString();
    }

    private static void printExplanation(String word) {
        String explanation = concepts.get(word);
        System.out.println();
        printSlow(ANSI_CYAN + "Explanation: " + ANSI_RESET, 20);
        printSlow(ANSI_YELLOW + explanation + ANSI_RESET, 15);
        System.out.println();
    }

    public static void printSlow(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println();
    }
}
