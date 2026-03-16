package trainer;

import java.util.ArrayList;
import java.util.List;

// This class represents a Linux command with bilingual support (EN + DE).
public class LinuxCommand {

    // --- Inner classes for structured bilingual data ---

    public static class Flag {
        public final String flag;
        public final String en;
        public final String de;
        public Flag(String flag, String en, String de) {
            this.flag = flag;
            this.en = en;
            this.de = de;
        }
    }

    public static class Example {
        public final String cmd;
        public final String en;
        public final String de;
        public Example(String cmd, String en, String de) {
            this.cmd = cmd;
            this.en = en;
            this.de = de;
        }
    }

    public static class Pitfall {
        public final String en;
        public final String de;
        public Pitfall(String en, String de) {
            this.en = en;
            this.de = de;
        }
    }

    // --- Core fields ---
    private final String id;
    private final String command;
    private final String syntax;
    private final Difficulty difficulty;
    private final List<String> distros;

    // --- Bilingual explanations ---
    private final String explainEnShort;
    private final String explainEnLong;
    private final String explainDeShort;
    private final String explainDeLong;

    // --- Rich content ---
    private final List<Flag> flags;
    private final List<Example> examples;
    private final List<Pitfall> pitfalls;
    private final List<String> accepted;

    // --- Full constructor (used by JsonLoader) ---
    public LinuxCommand(String id, String command, String syntax,
                        String explainEnShort, String explainEnLong,
                        String explainDeShort, String explainDeLong,
                        List<Flag> flags, List<Example> examples,
                        List<Pitfall> pitfalls, List<String> accepted,
                        List<String> distros, Difficulty difficulty) {
        this.id = id;
        this.command = command;
        this.syntax = syntax;
        this.explainEnShort = explainEnShort;
        this.explainEnLong = explainEnLong;
        this.explainDeShort = explainDeShort;
        this.explainDeLong = explainDeLong;
        this.flags = flags != null ? flags : new ArrayList<>();
        this.examples = examples != null ? examples : new ArrayList<>();
        this.pitfalls = pitfalls != null ? pitfalls : new ArrayList<>();
        this.accepted = accepted != null ? accepted : new ArrayList<>();
        this.distros = distros != null ? distros : new ArrayList<>();
        this.difficulty = difficulty;
    }

    // --- Legacy constructor (backward compatibility) ---
    public LinuxCommand(String command, String description, String example, Difficulty difficulty) {
        this(command, command, null,
             description, description,
             description, description,
             new ArrayList<>(),
             example != null ? singleExample(example) : new ArrayList<>(),
             new ArrayList<>(),
             singleAccepted(command),
             singleDistro("ALL"),
             difficulty);
    }

    public LinuxCommand(String command, String description, Difficulty difficulty) {
        this(command, description, null, difficulty);
    }

    private static List<Example> singleExample(String cmd) {
        List<Example> list = new ArrayList<>();
        list.add(new Example(cmd, "", ""));
        return list;
    }

    private static List<String> singleAccepted(String command) {
        List<String> list = new ArrayList<>();
        list.add(command);
        return list;
    }

    private static List<String> singleDistro(String distro) {
        List<String> list = new ArrayList<>();
        list.add(distro);
        return list;
    }

    // --- Getters ---

    public String getId() { return id; }
    public String getCommand() { return command; }
    public String getSyntax() { return syntax; }
    public Difficulty getDifficulty() { return difficulty; }
    public List<String> getDistros() { return distros; }

    public String getExplainEnShort() { return explainEnShort; }
    public String getExplainEnLong() { return explainEnLong; }
    public String getExplainDeShort() { return explainDeShort; }
    public String getExplainDeLong() { return explainDeLong; }

    public List<Flag> getFlags() { return flags; }
    public List<Example> getExamples() { return examples; }
    public List<Pitfall> getPitfalls() { return pitfalls; }
    public List<String> getAccepted() { return accepted; }

    // Legacy accessors used by backward-compat code
    public String getDescription() { return explainEnShort; }
    public String getExample() {
        return examples.isEmpty() ? null : examples.get(0).cmd;
    }

    /**
     * Returns the short explanation in the given language.
     * For BOTH, returns EN followed by DE on a new line.
     */
    public String getShortExplanation(Language lang) {
        if (lang == Language.GERMAN) {
            return explainDeShort != null && !explainDeShort.isEmpty() ? explainDeShort : explainEnShort;
        }
        if (lang == Language.ENGLISH) {
            return explainEnShort != null && !explainEnShort.isEmpty() ? explainEnShort : explainDeShort;
        }
        // BOTH
        String en = explainEnShort != null ? explainEnShort : "";
        String de = explainDeShort != null ? explainDeShort : "";
        return en + "\n    [DE] " + de;
    }

    @Override
    public String toString() {
        String result = command + " - " + explainEnShort;
        if (!examples.isEmpty()) {
            result += "\n    Example: " + examples.get(0).cmd;
        }
        return result;
    }

    /**
     * Checks whether the user's input matches any accepted answer.
     * Normalizes whitespace and ignores case.
     */
    public boolean matches(String input) {
        String normalized = input.trim().replaceAll("\\s+", " ");
        for (String acc : accepted) {
            if (normalized.equalsIgnoreCase(acc.trim())) {
                return true;
            }
        }
        return false;
    }
}
