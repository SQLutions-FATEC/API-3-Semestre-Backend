package com.sqlutions.altave.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class ViewConfig implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        String url = "jdbc:postgresql://localhost:5432/sistema-de-ponto";
        String user = "user";
        String password = "admin";

        String sql = """
                        DROP VIEW IF EXISTS analytics_summary;

                        CREATE OR REPLACE VIEW analytics_summary AS
                        SELECT
                            e.id AS employee_id,
                            c.date_time_in,
                            c.date_time_out,
                            EXTRACT(EPOCH FROM (c.date_time_out - c.date_time_in)) / 3600 AS total_hours_worked,
                            e.sex
                        FROM employee e
                        JOIN clock_in c ON c.employee_id = e.id;
""";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement stmt = connection.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("View criada com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao criar view analytics_summary: " + e.getMessage());
        }
    }
}
