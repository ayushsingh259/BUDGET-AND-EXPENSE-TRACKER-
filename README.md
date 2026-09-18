# ExpenseTrack

## Overview
ExpenseTrack is a small Java console application for recording personal income and expenses. It stores data in local text files so it can be run directly without a database, build tool, or external library.

## Problem Statement
People often lose track of daily spending when transactions are recorded only in memory or remembered informally. ExpenseTrack provides a simple way to save transactions, monitor a monthly budget, and view a spending summary.

## Objectives
- Record income and expenses.
- Search and delete saved transactions.
- Store data locally between program runs.
- Compare spending with a monthly budget.
- Demonstrate beginner-level Java and OOP concepts.

## Features
- Add expense and income transactions.
- View, search, and delete transactions.
- Validate amounts, required fields, and dates.
- Set and view a persistent monthly budget.
- Show income, expenses, balance, category totals, highest category, and budget status.

## Functional Modules
1. **Transaction Management:** add, view, search, and delete income or expense records.
2. **Budget Management:** set a monthly budget and compare it with total expenses.
3. **Reporting and Summary:** calculate totals, balance, category-wise spending, and budget warnings.

## Technologies Used
- Java 11 or later
- `ArrayList` and `HashMap`
- Standard Java file handling (`FileReader`, `FileWriter`, buffered streams)
- Standard Java date validation

No Maven, Gradle, database, JDBC, framework, or external dependency is required.

## Requirements
Install Java 11 or a later JDK and make sure the `java` command is available in the terminal.

## How to Run
From the project root, run:

```text
java Main.java
```

The application creates `data/expenses.txt` and `data/budget.txt` automatically on first run.

## Project Structure
```text
ExpenseTrack/
├── Main.java
├── Expense.java
├── ExpenseManager.java
├── BudgetManager.java
├── ReportGenerator.java
├── data/
│   ├── expenses.txt
│   └── budget.txt
├── docs/
│   ├── diagrams.md
│   ├── project-report.md
│   ├── requirements.md
│   └── testing.md
├── .gitignore
├── README.md
└── statement.md
```

## How Data Is Stored
Transactions are stored one per line in `data/expenses.txt` using this format:

```text
id|type|amount|category|description|date
```

The budget amount is stored in `data/budget.txt`. The program creates both files when needed. The pipe character is replaced in user text before saving so the simple file format remains readable.

## Testing
The project uses manual console test cases because the requested project has no build tool or external testing library. See [docs/testing.md](docs/testing.md) for the test plan and results that should be checked after each run.

## Future Enhancements
- Edit an existing transaction.
- Support separate budgets for different months or categories.
- Export a report to CSV.
- Add automated tests using a standard test library in a future version.

## Suggested Git Workflow
Use small commits such as:

```text
Create simple Java project
Add transaction file persistence
Add budget management
Add reporting and validation
Add project documentation
```
