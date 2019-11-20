package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbCreator {

    public static void main(String[] args) {
        Connection c = null;
        Statement stmt = null;
        String sql = "";

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "123"
            );

            stmt = c.createStatement();



            sql = "CREATE SCHEMA IF NOT EXISTS  SWAY_IN_BALANCE ";
            stmt.executeUpdate(sql);



            sql = "CREATE TABLE IF NOT EXISTS SWAY_IN_BALANCE.RESOURCE "
                    + " ( "
                    + " ID                         SERIAL PRIMARY KEY     NOT NULL, "
                    + " RESOURCE_NAME              VARCHAR(32)            NOT NULL  "
                    + " ) ";
            stmt.executeUpdate(sql);



            sql = "CREATE TABLE IF NOT EXISTS SWAY_IN_BALANCE.EVENT "
                    + " ( "
                    + " ID                           SERIAL PRIMARY KEY          NOT NULL, "
                    + " RESOURCE_ID                  INT                         NOT NULL, "
                    + " EVENT_NAME                   VARCHAR(100)                NOT NULL, "
                    + " EVENT_DATE                   DATE                        NOT NULL, "
                    + " COMMENT                      VARCHAR(250)                          "
                    + " ) ";
            stmt.executeUpdate(sql);



            sql = "ALTER TABLE SWAY_IN_BALANCE.EVENT "
                    + " ADD CONSTRAINT FK_RESOURCE "
                    + " FOREIGN KEY (RESOURCE_ID ) REFERENCES SWAY_IN_BALANCE.RESOURCE (ID) MATCH FULL "
                    + " ";
            stmt.executeUpdate(sql);



            stmt.close();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Schema and tables are created successfully");
    }
}
