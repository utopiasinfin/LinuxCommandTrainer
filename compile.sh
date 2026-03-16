#!/bin/bash

set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
BIN_DIR="$PROJECT_DIR/bin"
SRC_DIR="$PROJECT_DIR/src/trainer"
RESOURCES_DIR="$PROJECT_DIR/resources"
BUILD_STAMP="$BIN_DIR/.build-stamp"

print_step() {
    echo "==> $1"
}

require_command() {
    if ! command -v "$1" >/dev/null 2>&1; then
        echo "Error: required command '$1' was not found in PATH." >&2
        exit 1
    fi
}

compile_sources() {
    print_step "Compiling Java sources"
    mkdir -p "$BIN_DIR"
    javac -d "$BIN_DIR" "$SRC_DIR"/*.java
}

copy_resources() {
    if [ -d "$RESOURCES_DIR" ]; then
        print_step "Copying resources"
        rm -rf "$BIN_DIR/resources"
        cp -r "$RESOURCES_DIR" "$BIN_DIR/"
    fi
}

write_build_stamp() {
    touch "$BUILD_STAMP"
}

main() {
    require_command javac
    compile_sources
    copy_resources
    write_build_stamp
    echo "Compilation complete."
}

main "$@"
