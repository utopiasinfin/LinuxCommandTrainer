package trainer;

import java.util.*;

// Esta clase actúa como una "base de datos" de comandos Linux.
// Filtra los comandos por distribución y nivel de dificultad.
public class LinuxCommandsDatabase {

    // Devuelve una lista de comandos según la distribución (distro) y el nivel (difficulty)
    public static List<LinuxCommand> getCommandsForDistro(String distro, Difficulty level) {
        List<LinuxCommand> commands = new ArrayList<>();

        // ----------------------------------------
        // Comandos UNIVERSALES por dificultad
        // ----------------------------------------

        if (level == Difficulty.BEGINNER) {
            // Comandos esenciales, te salvan la vida si estás perdido en la terminal
            commands.add(new LinuxCommand("ls", "list directory contents", "ls -l", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("cd", "change directory", "cd /home", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("pwd", "print working directory", null, Difficulty.BEGINNER));
            commands.add(new LinuxCommand("mkdir", "create a directory", "mkdir new_folder", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("rmdir", "remove empty directory", "rmdir empty_folder", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("rm", "remove files", "rm file.txt", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("cp", "copy files or folders", "cp file.txt backup/", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("mv", "move or rename files", "mv file.txt file_old.txt", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("touch", "create an empty file", "touch myfile.txt", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("cat", "display file content", "cat file.txt", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("clear", "clear the terminal", null, Difficulty.BEGINNER));
            commands.add(new LinuxCommand("echo", "print a message", "echo Hello World", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("man", "show manual page", "man ls", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("history", "show command history", null, Difficulty.BEGINNER));
            commands.add(new LinuxCommand("whoami", "show current user", null, Difficulty.BEGINNER));
            commands.add(new LinuxCommand("date", "show current date and time", null, Difficulty.BEGINNER));
            commands.add(new LinuxCommand("df", "disk usage of mounted filesystems", "df -h", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("du", "show disk usage of files", "du -sh folder/", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("uname", "show system information", "uname -a", Difficulty.BEGINNER));
            commands.add(new LinuxCommand("exit", "close the terminal session", null, Difficulty.BEGINNER));
        }

        if (level == Difficulty.INTERMEDIATE) {
            // Comandos más técnicos, útiles para buscar, filtrar, comprimir…
            commands.add(new LinuxCommand("grep", "search inside files", "grep 'text' file.txt", Difficulty.INTERMEDIATE));
            commands.add(new LinuxCommand("find", "find files and folders", "find /home -name '*.txt'", Difficulty.INTERMEDIATE));
            commands.add(new LinuxCommand("tar", "compress/extract archives", "tar -czvf archive.tar.gz folder/", Difficulty.INTERMEDIATE));
            commands.add(new LinuxCommand("head", "show first lines of a file", "head -n 10 file.txt", Difficulty.INTERMEDIATE));
            commands.add(new LinuxCommand("tail", "show last lines of a file", "tail -f logs.txt", Difficulty.INTERMEDIATE));
            commands.add(new LinuxCommand("ps", "list running processes", "ps aux", Difficulty.INTERMEDIATE));
            commands.add(new LinuxCommand("kill", "terminate a process by PID", "kill 1234", Difficulty.INTERMEDIATE));
            commands.add(new LinuxCommand("chmod", "change permissions", "chmod 755 script.sh", Difficulty.INTERMEDIATE));
            commands.add(new LinuxCommand("chown", "change file owner", "chown user:group file", Difficulty.INTERMEDIATE));
            commands.add(new LinuxCommand("top", "interactive process viewer", null, Difficulty.INTERMEDIATE));
        }

        if (level == Difficulty.ADVANCED) {
            // Comandos de redes, administración de sistema, logs…
            commands.add(new LinuxCommand("ip a", "show IP addresses", null, Difficulty.ADVANCED));
            commands.add(new LinuxCommand("ping", "check network connectivity", "ping 8.8.8.8", Difficulty.ADVANCED));
            commands.add(new LinuxCommand("netstat", "show network connections", "netstat -tulnp", Difficulty.ADVANCED));
            commands.add(new LinuxCommand("ss", "modern netstat replacement", "ss -tulwn", Difficulty.ADVANCED));
            commands.add(new LinuxCommand("nmap", "network scanner", "nmap 192.168.1.0/24", Difficulty.ADVANCED));
            commands.add(new LinuxCommand("journalctl", "view system logs", "journalctl -xe", Difficulty.ADVANCED));
            commands.add(new LinuxCommand("systemctl", "control systemd services", "systemctl restart ssh", Difficulty.ADVANCED));
            commands.add(new LinuxCommand("uptime", "show system uptime", null, Difficulty.ADVANCED));
            commands.add(new LinuxCommand("hostname", "display or set hostname", null, Difficulty.ADVANCED));
            commands.add(new LinuxCommand("curl", "make web requests", "curl https://example.com", Difficulty.ADVANCED));
        }

       // Lista de comandos!

        switch (distro) {
            case "Debian/Ubuntu":
                if (level == Difficulty.BEGINNER) {
                    commands.add(new LinuxCommand("sudo apt update", "update package list", Difficulty.BEGINNER));
                }
                if (level == Difficulty.INTERMEDIATE) {
                    commands.add(new LinuxCommand("sudo apt upgrade", "upgrade installed packages", Difficulty.INTERMEDIATE));
                    commands.add(new LinuxCommand("sudo apt install", "install a package", "sudo apt install vim", Difficulty.INTERMEDIATE));
                }
                if (level == Difficulty.ADVANCED) {
                    commands.add(new LinuxCommand("sudo apt remove", "remove a package", "sudo apt remove nano", Difficulty.ADVANCED));
                    commands.add(new LinuxCommand("apt search", "search for packages", "apt search firefox", Difficulty.ADVANCED));
                }
                break;

            case "Arch/Manjaro":
                if (level == Difficulty.BEGINNER) {
                    commands.add(new LinuxCommand("sudo pacman -Sy", "update package database", Difficulty.BEGINNER));
                }
                if (level == Difficulty.INTERMEDIATE) {
                    commands.add(new LinuxCommand("sudo pacman -Syu", "upgrade all packages", Difficulty.INTERMEDIATE));
                    commands.add(new LinuxCommand("sudo pacman -S", "install a package", "sudo pacman -S vim", Difficulty.INTERMEDIATE));
                }
                if (level == Difficulty.ADVANCED) {
                    commands.add(new LinuxCommand("sudo pacman -R", "remove a package", "sudo pacman -R nano", Difficulty.ADVANCED));
                    commands.add(new LinuxCommand("pacman -Ss", "search for packages", "pacman -Ss firefox", Difficulty.ADVANCED));
                }
                break;

            case "Fedora":
                if (level == Difficulty.BEGINNER) {
                    commands.add(new LinuxCommand("sudo dnf check-update", "check for updates", Difficulty.BEGINNER));
                }
                if (level == Difficulty.INTERMEDIATE) {
                    commands.add(new LinuxCommand("sudo dnf upgrade --refresh", "upgrade all packages", Difficulty.INTERMEDIATE));
                    commands.add(new LinuxCommand("sudo dnf install", "install a package", "sudo dnf install vim", Difficulty.INTERMEDIATE));
                }
                if (level == Difficulty.ADVANCED) {
                    commands.add(new LinuxCommand("sudo dnf remove", "remove a package", "sudo dnf remove nano", Difficulty.ADVANCED));
                    commands.add(new LinuxCommand("dnf search", "search for packages", "dnf search firefox", Difficulty.ADVANCED));
                }
                break;
        }

        return commands;
    }
}

