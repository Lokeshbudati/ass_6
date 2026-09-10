package com.example;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Automated Employee Access Verification Batch ===");

        List<Employee> employeeBatch = new ArrayList<Employee>();
        employeeBatch.add(new Employee("E001", "Alice Jenkins", 25, "IT", "Active", true, 4));
        employeeBatch.add(new Employee("E002", "Bob Smith", 21, "HR", "Active", true, 2));
        employeeBatch.add(new Employee("E003", "Charlie Brown", 30, "Finance", "Active", true, 1));
        employeeBatch.add(new Employee("E004", "David Miller", 19, "Marketing", "Inactive", false, 1));

        int targetResourceLevel = 3;

        for (Employee emp : employeeBatch) {
            EvaluationResult result = AccessEvaluator.evaluateAccess(emp, targetResourceLevel);

            System.out.println("\n-------------------------------------------");
            System.out.println("ID     : " + emp.getId());
            System.out.println("Name   : " + emp.getName());
            System.out.println("Status : " + result.getStatus());
            
            if (!result.getRejectionReasons().isEmpty()) {
                System.out.println("Reasons:");
                for (String reason : result.getRejectionReasons()) {
                    System.out.println("  - " + reason);
                }
            }
        }
        System.out.println("\n-------------------------------------------");
    }
}
