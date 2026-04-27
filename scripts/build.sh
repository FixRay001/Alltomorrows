#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BUILD_DIR="$ROOT_DIR/build"
CLASSES_DIR="$BUILD_DIR/classes"
JAR_PATH="$BUILD_DIR/alltomorrows-part1-beta.jar"

rm -rf "$CLASSES_DIR"
mkdir -p "$CLASSES_DIR"

javac -encoding UTF-8 -d "$CLASSES_DIR" $(find "$ROOT_DIR/src/main/java" -name "*.java")

jar --create --file "$JAR_PATH" --main-class com.alltomorrows.menu.MainMenuApp -C "$CLASSES_DIR" .

echo "Built $JAR_PATH"
