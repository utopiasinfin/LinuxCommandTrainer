#!/bin/bash
cd "$(dirname "$0")"
mkdir -p bin
javac -d bin src/trainer/*.java
# Copy resources to bin/ so they are available on the classpath at runtime
if [ -d "resources" ]; then
    cp -r resources bin/
fi
echo "Compilation complete."
