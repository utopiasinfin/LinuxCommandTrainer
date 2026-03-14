# Linux Command Trainer

Linux Command Trainer is a simple Java console application designed to help users learn essential Linux commands interactively.

It was developed as part of a training project during my education as a system integrator (Fachinformatiker für Systemintegration).

## What does it do?

- Allows users to choose a Linux distribution: Debian, Arch, or Fedora
- Offers three difficulty levels: Beginner, Intermediate, and Advanced
- Displays a list of useful Linux commands based on the selected combination

## How to run

1. Compile the program:

    ./compile.sh

2. Run the program:

    ./run.sh

3. Optional: set up a global alias for easy access

    alias linuxtrainer='./run.sh'

## Project structure

LinuxCommandTrainer/
├── src/trainer/
│   ├── LinuxCommand.java
│   ├── LinuxCommandsDatabase.java
│   └── LinuxCommandTrainer.java
├── run.sh
├── compile.sh
└── (soon) resources/commands.json

## What's next?

- Load command data from an external JSON file
- Add an interactive quiz mode
- Connect with a graphical version (JavaFX: LinuxDirectoryExplorer)

