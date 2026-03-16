# Linux Command Trainer

An offline, bilingual (English + German) interactive Java console application for learning Linux commands.
Developed as part of a training project during education as a system integrator (Fachinformatiker für Systemintegration).

## Features

- **Language selection** at startup: English, Deutsch, or Both (EN+DE) — no default, must choose explicitly
- **Distro selection**: Debian/Ubuntu, Arch/Manjaro, Fedora
- **Three difficulty levels**: Beginner, Intermediate, Advanced
- **~40 core universal commands** loaded from an offline JSON data file, each with:
  - Bilingual short and long explanations (English + German, informal "du")
  - Syntax string
  - Multiple examples per command (with EN+DE descriptions)
  - Common flags/options (with EN+DE meanings)
  - Safety notes / pitfalls for risky commands (`rm`, `chmod`, `chown`, `find`, `xargs`, etc.)
- **Four learning modes** in the console UI:
  1. **Show command list** — browse commands with short descriptions and one example
  2. **Learn mode** — detailed flashcard-style view: syntax, long explanation, flags, examples, pitfalls
  3. **Command quiz** — read a description, type the command; accepts aliases and normalizes whitespace
  4. **Flags quiz** — multiple-choice quiz: identify the meaning of a command flag from four options

## How to run

1. Compile the program:

       ./compile.sh

2. Run the program:

       ./run.sh

3. Optional: set up a global alias

       alias linuxtrainer='cd /path/to/LinuxCommandTrainer && ./run.sh'

## Project structure

    LinuxCommandTrainer/
    |-- resources/
    |   +-- commands.json           # Bilingual command dataset (~40 commands)
    |-- src/trainer/
    |   |-- Difficulty.java         # Enum: BEGINNER / INTERMEDIATE / ADVANCED
    |   |-- Language.java           # Enum: ENGLISH / GERMAN / BOTH
    |   |-- LinuxCommand.java       # Command model with bilingual fields
    |   |-- LinuxCommandsDatabase.java   # Loads + filters commands from JSON
    |   |-- JsonLoader.java         # Offline JSON parser (no external dependencies)
    |   |-- LinuxCommandTrainer.java     # Main UI: menus and all learning modes
    |   +-- LinuxHangman.java       # Bonus: Linux-themed hangman game
    |-- compile.sh                  # Compiles Java sources and copies resources to bin/
    +-- run.sh                      # Runs the compiled application

## Command dataset

The dataset is stored in `resources/commands.json` and contains 40 core universal Linux commands:

| Level        | Count | Commands                                                       |
|--------------|-------|----------------------------------------------------------------|
| Beginner     | 16    | pwd, ls, cd, mkdir, rmdir, touch, cp, mv, rm, cat, less, head, tail, echo, man, history |
| Intermediate | 14    | grep, find, wc, sort, uniq, cut, tr, xargs, tee, tar, chmod, chown, ln, top |
| Advanced     | 10    | ps, kill, df, du, uname, hostname, ip, ss, curl, systemctl    |

All commands include bilingual descriptions (EN + DE using informal "du"), flags, examples, and safety notes.

## Technical notes

- **Offline only**: no network access, no API keys required
- **No external dependencies**: JSON is parsed with a built-in minimal parser (no Maven/Gradle needed)
- **Java SE compatible**: tested with Java 11+
- Resources are copied to `bin/resources/` during compilation so they are available on the classpath at runtime

