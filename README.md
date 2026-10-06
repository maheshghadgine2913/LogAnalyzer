# Log Analyzer (Java + MySQL)

A Java console application that reads application log files, stores them in a MySQL database and reports error trends. It is built to help investigate application issues using log and database information.

## Features
- Parses log lines (time, level, message) into Java objects
- Stores log entries in MySQL using JDBC
- Reports: count by level, most frequent error, errors per day, latest 10 errors, keyword search
- Console menu to load a log file and run the reports

## Tech Stack
Java 21, Maven, MySQL, JDBC, Git/GitHub

## Design

```
Main (console menu)
  -> ReportService / LogDAO -> MySQL
  -> LogParser -> LogEntry
```

| Class | Purpose |
|---|---|
| `Main` | Console menu that takes the user's choice |
| `LogParser` | Converts one log line into a `LogEntry` |
| `LogEntry` | Holds the time, level and message of one log line |
| `LogDAO` | Saves entries to MySQL and clears old data |
| `ReportService` | Runs the SQL report queries |
| `DBConnection` | Creates the MySQL connection from `config.properties` |

OOP concepts used: encapsulation (private fields with getters), abstraction (DAO and service classes hide SQL from `Main`).

## Menu Options
1. Load log file into the database
2. Show count by level
3. Show most frequent error
4. Show errors per day
5. Show latest 10 errors
6. Search by keyword
7. Exit

## How to Run
1. Install Java 21, Maven and MySQL.
2. Run `schema.sql` in MySQL to create the database and table.
3. Copy `config.example.properties` to `config.properties` and enter your MySQL username and password.
4. Run `Main.java` and choose option 1 to