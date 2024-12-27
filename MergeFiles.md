# Creating and Merging Branches in Git

This document outlines how to create a new branch from the main branch in Git, make changes, and then merge those changes back into the main branch.

## 1. Creating a New Branch

*   **Ensure you are on the main branch:**

    ```bash
    git checkout main
    ```

*   **Create a new branch and switch to it:**

    ```bash
    git checkout -b <new_branch_name>
    ```

    Replace `<new_branch_name>` with a descriptive name for your branch (e.g., `feature/new-login`, `bugfix/issue-123`).

## 2. Making Changes

*   **Perform your work:** Edit files, add new files, etc.

*   **Stage your changes:**

    ```bash
    git add .         # Stages all changes
    git add <file>    # Stages a specific file
    ```

*   **Commit your changes:**

    ```bash
    git commit -m "A descriptive commit message"
    ```

## 3. Merging Back into the Main Branch

*   **Switch back to the main branch:**

    ```bash
    git checkout main
    ```

*   **Update your local main branch:** It's best practice to update your local main branch with the remote main branch before merging.

    ```bash
    git pull origin main
    ```

*   **Merge your branch into main:**

    ```bash
    git merge <new_branch_name>
    ```

*   **Resolve any merge conflicts:** If conflicts arise (changes to the same lines of code in both branches), Git will mark them in the affected files. You'll need to manually edit these files to resolve the conflicts, then stage and commit the changes.

*   **Push the changes to the remote repository:**

    ```bash
    git push origin main
    ```

## Example

```bash
git checkout main                     # Switch to main
git checkout -b feature/new-button    # Create and switch to a new branch
# Make changes to files
git add .                             # Stage changes
git commit -m "Added a new button to the UI" # Commit changes
git checkout main                     # Switch back to main
git pull origin main                  # Update local main
git merge feature/new-button          # Merge the new branch
git push origin main                  # Push the changes