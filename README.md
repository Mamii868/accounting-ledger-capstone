# Accounting Ledger

A Java console app for tracking ledger transactions for a small business.

## What the application does

The application lets users:

- Record deposits
- Record payments
- View the account ledger
- Search and filter transactions
- Generate financial reports, including:
  - Month-to-Date
  - Previous Month
  - Year-to-Date
  - Previous Year
  - Vendor Search
- Manage user and transaction records
- Store financial data in a MySQL relational database

The application uses a MySQL database (`pennywisedb.sql`) to securely store user and transaction information.

Transactions are loaded from and saved to `src/main/resources/transactions.csv`.

## Requirements

Install these tools on the machine before starting the app:
- Java 17
- Maven 3.9 or newer

## How to start the application

1. Open a terminal.
2. Go to the project folder:

   ```bash
   cd /path/to/accounting-ledger-capstone
   ```

3. Compile the application:
   ```bash
   mvn clean compile
   ```
   
4. Start the app from the repository root:

   ```bash
   mvn exec:java -Dexec.mainClass=com.pluralsight.LedgerApp
   ```

The app opens an interactive menu in the terminal where you can add transactions and view reports.
Use the arrow and enter keys to navigate through the app.
## Troubleshooting

### Application will not compile

Run:

```bash
mvn clean install
```

to restore all required dependencies.

### Database connection issues

Verify that:

- MySQL is running.
- The `pennywisedb` database has been imported successfully.
- Your database connection settings are correct.

### Missing dependencies

Refresh the Maven project and run:

```bash
mvn clean install
```


## FAQ

- Why was there a class not found error compiling the code?

  Double check that the project has the Jline dependencies downloaded
