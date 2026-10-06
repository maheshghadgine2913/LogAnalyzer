package loganalyzer;

import com.mysql.cj.log.Log;

import java.sql.*;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class LogDAO {
    public void saveAll(List<LogEntry> entries) throws SQLException {
        String sql = "INSERT INTO logs (log_time, level, message) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (LogEntry entry : entries) {
                ps.setTimestamp(1, Timestamp.valueOf(entry.getLogTime()));
                ps.setString(2,entry.getLevel());
                ps.setString(3,entry.getMessage());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public void clearAll() throws SQLException {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate("DELETE FROM logs");
        }
    }
}
