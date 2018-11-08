package com.company;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String host = "localhost";
        String port = "1433";
        String db = "run";
        String username = "";
        String password = "";

        String sql = "";

        for (int i = 0; i < args.length; i++){
            switch(args[i]){
                case "-h":
                case "--host":
                    host = args[++i];
                    break;
                case "-p":
                case "--port":
                    port = args[++i];
                    break;
                case "-d":
                case "--db":
                    db = args[++i];
                    break;
                case "-u":
                case "--username":
                    username = args[++i];
                    break;
                case "-a":
                case "--password":
                    password = args[++i];
                    break;
                case "-s":
                case "--sql":
                    sql = args[++i];
                    break;
            }
        }

        System.out.println("host=" + host);
        System.out.println("port=" + port);
        System.out.println("username=" + username);
        System.out.println("password=" + password);
        System.out.println("sql='" + sql +"'");

        try {
            String url = String.format("jdbc:sqlserver://%s:%s;DatabaseName=%s", host, port, db);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            ResultSetMetaData metaData = rs.getMetaData();

            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.print(metaData.getColumnName(i + 1) + ",");
            }
            System.out.println();

            System.out.println("----------------------------------------------------------");

            while (rs.next()) {
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    System.out.print(rs.getString(i + 1) + ",");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
