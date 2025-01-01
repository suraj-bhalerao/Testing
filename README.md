# ATCU4G Automation Project

**Author:** Suraj Bhalerao  
**Date of Creation:** 01/01/2025  
**Purpose:** A repository for automating the ATCU 4G Web Project using Selenium, Java, and TestNG. This project is designed to streamline testing processes, ensure application reliability, and provide an efficient framework for maintaining high-quality code standards.

---

## Initial Setup (One-Time)

1.  **Get the Repository URL:** Copy the repository URL (HTTPS or SSH).

2.  **Clone the Repository (Local Setup):** Open your terminal and navigate to your desired project directory. Clone the repository using:

    ```bash
    	git clone <repository_url>
    ```
    **Note:** Replace `<repository_url>` with the URL of the project you want to clone to your local repository.

---

## Daily Workflow

1.  **Pull Changes (Start of Day/Before Making Changes):** Always start by pulling the latest changes from the remote repository to avoid conflicts:

    ```bash
    git pull origin main  # Or git pull origin <your_main_branch_name> if it's not 'main', e.g., 'master'
    ```

2.  **Create a Branch (For Each Feature/Bug Fix):** Create a new branch for each feature or bug fix to isolate changes:

    ```bash
    git checkout -b <feature_name>  # Example: git checkout -b fix-login-bug
    ```

3.  **Make Changes and Commit:** Make your changes, stage them, and commit with a descriptive message:

    ```bash
    git add .               # Stage all changes
    git add <specific_file> # Stage a specific file
    git commit -m "Descriptive commit message" # Example: git commit -m "Fixed login form validation"
    ```

4.  **Push Your Branch:** Push your branch to the remote repository:

    ```bash
    git push origin <feature_name>
    ```

5.  **Create a Pull Request (PR):** On the platform's website, create a pull request to merge your branch into the main branch. Provide a clear title and description for the PR, explaining the changes made.

6.  **Code Review:** Team members review the code in the pull request, providing feedback and suggestions. Address any feedback and push updated commits to your branch (which will automatically update the PR).

7.  **Merge the Pull Request:** Once the code review is approved, the pull request is merged into the main branch.

8.  **Delete Your Branch (Optional, but Recommended):** After the PR is merged, delete your local and remote branches:

    ```bash
    git checkout main
    git pull origin main
    git branch -d <feature_name>          # Delete local branch
    git push origin --delete <feature_name> # Delete remote branch
    ```

---

## Important Considerations

*   **Descriptive Commit Messages:** Use clear and concise commit messages that explain the purpose of the changes.
*   **Keep Branches Up-to-Date:** Regularly pull changes from the main branch into your feature branches to minimize merge conflicts:

    ```bash
    git checkout <feature_name>
    git pull origin main
    ```

*   **Resolve Conflicts Carefully:** If conflicts arise during a merge or pull, resolve them carefully and test your changes thoroughly.
*   **Communicate with Your Team:** Keep your team informed about your progress and any potential issues.
*   **Use a Consistent Branching Strategy:** Stick to a consistent branching strategy (e.g., Gitflow) for better organization.

---

## Common Git Commands

*   `git status`: Shows the status of your changes.
*   `git log`: Shows the commit history.
*   `git diff`: Shows the differences between files.
*   `git branch`: Lists branches.
*   `git checkout`: Switches branches.
*   `git merge`: Merges branches.

---

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

---

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

---

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

*   **Resolve any merge conflicts:** If conflicts arise (changes to the same lines of code in both branches), Git will mark them in the affected files. Manually edit these files to resolve the conflicts, then stage and commit the changes.

*   **Push the changes to the remote repository:**

    ```bash
    git push origin main
    ```

---

## Example Workflow

```bash
git checkout main                               # Switch to main
git checkout -b feature/new-button             # Create and switch to a new branch

# Make changes to files
git add .                                      # Stage changes
git commit -m "Added a new button to the UI"  # Commit changes
git checkout main                              # Switch back to main
git pull origin main                           # Update local main
git merge feature/new-button                   # Merge the new branch
git push origin main                           # Push the changes
```

---

**Notes:**
* Ensure consistent formatting and grammar.
* Maintain clarity in instructions to improve user comprehension.
* If possible, automate workflows for improved efficiency.

---

