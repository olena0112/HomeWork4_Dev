package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.file.Path;


public class DatabasePopulateService {
    public static void main(String[] args) {
        String populateScriptPath = "sql/populate_db.sql";

        try {
            String populateScript = readScript(populateScriptPath);
            executeScript(populateScript);
            System.out.println("Database populated successfully.");
        } catch (IOException e) {
            System.err.println("Failed to read the populate script: " + populateScriptPath);
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to execute the populate script.");
            e.printStackTrace();
        }
    }

    private static String readScript(String scriptPath) throws IOException {
        Path path = Paths.get(scriptPath);
        byte[] bytes = Files.readAllBytes(path);
        return new String(bytes);
    }

    private static void executeScript(String script) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            statement.execute(script);
        }
    }
}


