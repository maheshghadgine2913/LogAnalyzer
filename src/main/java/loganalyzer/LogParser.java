package loganalyzer;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogParser {
    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public LogEntry parseLine(String line) {
        String[] parts = line.split(" ",4);

        if (parts.length < 4) {
            return null;
        }

        try {
            LocalDateTime time = LocalDateTime.parse(parts[0] + " " + parts[1], FORMAT);
            String level = parts[2];
            String message = parts[3];
            return new LogEntry(time,level,message);
        }catch (DateTimeException e) {
            return null;
        }
    }
}
