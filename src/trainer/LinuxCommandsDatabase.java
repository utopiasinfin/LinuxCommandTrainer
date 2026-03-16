package trainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Loads commands from the JSON data file and filters them
 * by Linux distribution and difficulty level.
 */
public class LinuxCommandsDatabase {

    private static List<LinuxCommand> allCommands = null;

    private static List<LinuxCommand> getAllCommands() {
        if (allCommands == null) {
            allCommands = JsonLoader.loadCommands();
        }
        return allCommands;
    }

    /**
     * Returns commands that match the given distro and difficulty level.
     * Commands tagged "ALL" are returned for every distro.
     */
    public static List<LinuxCommand> getCommandsForDistro(String distro, Difficulty level) {
        List<LinuxCommand> result = new ArrayList<>();
        for (LinuxCommand cmd : getAllCommands()) {
            if (cmd.getDifficulty() == level) {
                List<String> distros = cmd.getDistros();
                if (distros.contains("ALL") || distros.contains(distro)) {
                    result.add(cmd);
                }
            }
        }
        return result;
    }
}

