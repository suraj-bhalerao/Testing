#!/bin/bash

# Enable debugging
set -x

# Directory to scan
TARGET_DIR="/d/Testing/testing_updated"

# Folder names to delete (space-separated list)
FOLDERS_TO_DELETE=("logs" "screenshots" "test-output" "test-results")

# Navigate to the target directory
cd "$TARGET_DIR" || { echo "Failed to navigate to $TARGET_DIR"; exit 1; }

# Delete specific folders if they exist
for folder in "${FOLDERS_TO_DELETE[@]}"; do
    if [ -d "$folder" ]; then
        echo "Deleting folder: $folder"
        rm -rf "$folder"
    fi
done

# Navigate to project directory
PROJECT_DIR="/d/Testing/testing_updated"
cd "$PROJECT_DIR" || { echo "Failed to navigate to $PROJECT_DIR"; exit 1; }

# Run Maven clean install
mvn clean install

# Wait for 30 seconds
sleep 30

# Git operations with wait intervals
git add .
sleep 20
git commit -m "Suraj: Auto commit changes by shell"
sleep 20
git push -u main
