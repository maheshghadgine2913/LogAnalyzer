package loganalyzer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportService {

    public void countByLevel() throws SQLException {
        String sql = "SELECT level, COUNT(*) AS total FROM logs GROUP BY level ORDER BY total DESC";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("--- Count by level ---");
            while (rs.next()) {
                System.out.println(rs.getString("level") + ": " + rs.getInt("total"));
            }
        }
    }

    public void mostFrequentError() throws SQLException {
        String sql = "SELECT message, COUNT(*) AS total FROM logs "
                + "WHERE level = 'ERROR' GROUP BY message ORDER BY total DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("--- Most frequent error ---");
            if (rs.next()) {
                System.out.println(rs.getString("message") + " (" + rs.getInt("total") + " times)");
            } else {
                System.out.println("No errors found.");
            }
        }
    }

    public void errorsPerDay() throws SQLException {
        String sql = "SELECT DATE(log_time) AS day, COUNT(*) AS total FROM logs "
                + "WHERE level = 'ERROR' GROUP BY DATE(log_time) ORDER BY day";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("--- Errors per day ---");
            while (rs.next()) {
                System.out.println(rs.getString("day") + ": " + rs.getInt("total"));
            }
        }
    }

    public void latestErrors() throws SQLException {
        String sql = "SELECT log_time, message FROM logs "
                + "WHERE level = 'ERROR' ORDER BY log_time DESC LIMIT 10";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("--- Latest 10 errors ---");
            while (rs.next()) {
                System.out.println(rs.getString("log_time") + "  " + rs.getString("message"));
            }
        }
    }

    public void searchByKeyword(String keyword) throws SQLException {
        String sql = "SELECT log_time, level, message FROM logs "
                + "WHERE message LIKE ? ORDER BY log_time DESC LIMIT 20";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("--- Search results for '" + keyword + "' (max 20) ---");
                boolean found = false;
                while (rs.next()) {
                    found = true;
                    System.out.println(rs.getString("log_time") + " [" + rs.getString("level")
                            + "] " + rs.getString("message"));
                }
                if (!found) {
                    System.out.println("No matching entries.");
                }
            }
        }
    }
}
