#!/bin/bash
cd "$(dirname "$0")"
mkdir -p bin
javac -d bin src/trainer/*.java
echo "Compilación completada."
