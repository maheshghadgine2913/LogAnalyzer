package loganalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogParser parser = new LogParser();
        LogDAO dao = new LogDAO();
        ReportService reports = new ReportService();

        System.out.println("=================================");
        System.out.println("   Welcome to Log Analyzer");
        System.out.println("=================================");

        while (true) {
            System.out.println();
            System.out.println("1. Load log file into the database");
            System.out.println("2. Show count by level");
            System.out.println("3. Show most frequent error");
            System.out.println("4. Show errors per day");
            System.out.println("5. Show latest 10 errors");
            System.out.println("6. Search by keyword");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        loadFile(parser, dao, sc);
                        break;
                    case "2":
                        reports.countByLevel();
                        break;
                    case "3":
                        reports.mostFrequentError();
                        break;
                    case "4":
                        reports.errorsPerDay();
                        break;
                    case "5":
                        reports.latestErrors();
                        break;
                    case "6":
                        System.out.print("Enter keyword: ");
                        String keyword = sc.nextLine().trim();
                        reports.searchByKeyword(keyword);
                        break;
                    case "7":
                        System.out.println("Goodbye!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter 1 to 7.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    private static void loadFile(LogParser parser, LogDAO dao, Scanner sc) throws SQLException {
        System.out.print("Enter log file name (press Enter for app.log): ");
        String fileName = sc.nextLine().trim();
        if (fileName.isEmpty()) {
            fileName = "app.log";
        }

        List<LogEntry> entries = new ArrayList<>();
        int skipped = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry entry = parser.parseLine(line);
                if (entry != null) {
                    entries.add(entry);
                } else {
                    skipped++;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
            return;
        }

        dao.clearAll();
        dao.saveAll(entries);
        System.out.println(entries.size() + " entries saved. Skipped lines: " + skipped);
    }
}
