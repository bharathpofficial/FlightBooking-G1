# FlightBooking-G1 Automation Framework

## 🚀 How to Collaborate?

Welcome to the FlightBooking-G1 test automation project! To ensure effective team collaboration, please follow these standardized steps:

### 1. Clone the Repository

Clone this repository to your local machine:

```bash
git clone git@github.com:bharathpofficial/FlightBooking-G1.git
cd FlightBooking-G1
```

### 2. Create a New Feature Branch

Before starting any new work, **create a new branch** based off `main`. Never commit directly to `main`.

```bash
git checkout -b feature/<your-feature-name>
```

- Replace `<your-feature-name>` with a short description, e.g., `feature/booking-tests` or `bugfix/login-validation`.

### 3. Make and Test Your Changes Locally

- Develop and update code in your feature branch.
- Run local tests to ensure everything works:
    - Command: `mvn clean test`
    - Validate test reports and fix all failures before pushing.

### 4. Stage and Commit Changes

Track and commit your changes with meaningful messages:

```bash
git add .
git commit -m "Short, clear summary of what you changed"
```

### 5. Push to Your Branch on GitHub

Push your branch and code to the remote repository:

```bash
git push origin feature/<your-feature-name>
```

### 6. Create a Pull Request (PR)

- Go to the GitHub repository page.
- Click **“Compare & Pull Request”** for your branch.
- Make sure the base is `main` and compare with your branch.
- Add a PR description and assign reviewers.
- Wait for CI and code review checks to pass.

### 7. Respond to Review Feedback

- Address any comments or requested changes.
- Commit and push updates to your branch—your PR will update automatically.

### 8. Keep Your Branch Updated with Main

Regularly sync your feature branch with latest `main` changes:

```bash
git checkout main
git pull origin main
git checkout feature/<your-feature-name>
git merge main
```

### 9. Merge Only After Passing Checks

Only merge your PR when:
- All tests pass.
- All reviewers have approved.
- There are no unresolved conflicts.

---
