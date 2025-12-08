package com.orangehrm.utilities;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/orangehrm";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static Connection getDBConnection() {
        try {
            System.out.println("Connecting to database...");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connected to database successfully!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Error connecting to database!");
            e.printStackTrace();
            return null;
        }
    }

    //Get the employee details from DB and store in a map
    public static Map<String,String> getEmployeeDetails(String employeeId) {
        String query = "SELECT emp_firstname, emp_middle_name, emp_lastname FROM hr_hr_employees WHERE employee_id =" + employeeId;

        Map<String, String> employeeDetails = new HashMap<>();

        try (Connection conn = getDBConnection();
             Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(query)) {
             System.out.println("Executing query: " + query);

             if(result.next()) {
                 String firstName = result.getString("emp_firstname");
                 String middleName = result.getString("emp_middle_name");
                 String lastName = result.getString("emp_lastname");

                 //Store in a map
                 employeeDetails.put("firstName", firstName);
                 employeeDetails.put("middleName", middleName!=null?middleName:"");
                 employeeDetails.put("lastName", lastName);

                 System.out.println("Query successful!");
                 System.out.println("Employee Data fetched successfully!");
             } else {
                 System.out.println("Employee not found!");
             }
        } catch (Exception e) {
            System.out.println("Error getting employee details!");
            e.printStackTrace();
        }
        return employeeDetails;

    }



}
