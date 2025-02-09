#!/bin/bash

# Enable debugging
#set -x

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
echo "Cleaning and installing the maven repository"
mvn clean install

# Wait for 30 seconds
sleep 30

# Check which files have been changed
git diff
sleep 20

# Check which files are being staged
git status 
sleep 20

# Git operations with wait intervals
git add .
sleep 20

# Get the current user's name
USER_NAME=$(git config user.name)
 
# Get changed file names (limit to first 10 for readability)
CHANGED_FILES=$(git diff --name-only | head -n 10 | tr '\n' ' ')
 
# Generate dynamic commit message
COMMIT_MESSAGE="Push by: $USER_NAME | Updated files: $CHANGED_FILES"
 
# Commit changes with generated message
git commit -m "$COMMIT_MESSAGE"
sleep 20

# pushing the changes onto main branch
git push -u main
sleep 20 

# Check log of commit
git log --oneline
