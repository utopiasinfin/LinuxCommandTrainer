package trainer;

// Esta clase representa un comando de Linux
public class LinuxCommand {

    // --- Atributos privados ---
    private String command;       // El comando (ej: "ls")
    private String description;   // Qué hace el comando
    private String example;       // Un ejemplo de uso (puede ser null)
    private Difficulty difficulty; // Nivel de dificultad del comando

    // --- Constructor completo ---
    public LinuxCommand(String command, String description, String example, Difficulty difficulty) {
        this.command = command;
        this.description = description;
        this.example = example;
        this.difficulty = difficulty;
    }

    // --- Constructor más corto si no hay ejemplo ---
    public LinuxCommand(String command, String description, Difficulty difficulty) {
        this(command, description, null, difficulty);
    }

    // --- Métodos para acceder a los atributos (getters) ---
    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getExample() {
        return example;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    // --- toString() sirve para mostrar el comando de forma legible ---
    @Override
    public String toString() {
        String result = command + " - " + description;
        if (example != null) {
            result += "\n    Example: " + example;
        }
        return result;
    }

    // --- Método útil si necesitas comparar la respuesta del usuario ---
    public boolean matches(String input) {
        return input.trim().equals(command);
    }
}
