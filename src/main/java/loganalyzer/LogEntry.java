package loganalyzer;

import java.time.LocalDateTime;

public class LogEntry {
    private LocalDateTime logTime;
    private String level;
    private String message;

    public LogEntry(LocalDateTime logTime, String level, String message){
        this.logTime = logTime;
        this.level = level;
        this.message = message;
    }

    public  LocalDateTime getLogTime(){
        return logTime;
    }

    public String getLevel(){
        return level;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString() {
        return logTime + "[" + level + "]" + message;
    }

}
