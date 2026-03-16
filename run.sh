#!/bin/bash

set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
BIN_DIR="$PROJECT_DIR/bin"
BUILD_STAMP="$BIN_DIR/.build-stamp"
MAIN_CLASS="$BIN_DIR/trainer/LinuxCommandTrainer.class"
MAIN_CLASS_NAME="trainer.LinuxCommandTrainer"

print_step() {
	echo "==> $1"
}

require_command() {
	if ! command -v "$1" >/dev/null 2>&1; then
		echo "Error: required command '$1' was not found in PATH." >&2
		exit 1
	fi
}

build_is_stale() {
	if [ ! -f "$MAIN_CLASS" ] || [ ! -f "$BUILD_STAMP" ]; then
		return 0
	fi

	if find "$PROJECT_DIR/src" "$PROJECT_DIR/resources" -type f -newer "$BUILD_STAMP" 2>/dev/null | grep -q .; then
		return 0
	fi

	return 1
}

ensure_build() {
	if build_is_stale; then
		print_step "Build artifacts are missing or out of date"
		"$PROJECT_DIR/compile.sh"
	fi
}

run_application() {
	print_step "Starting Linux Command Trainer"
	exec java -cp "$BIN_DIR" "$MAIN_CLASS_NAME"
}

main() {
	require_command java
	ensure_build
	run_application
}

main "$@"
