package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

   
    public static class Employee {
        private String id;
        private String name;
        private int age;
        private String department;
        private String employmentStatus; 
        private boolean isIdValid;
        private int securityClearanceLevel;

        public Employee(String id, String name, int age, String department, 
                        String employmentStatus, boolean isIdValid, int securityClearanceLevel) {
            if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("ID cannot be empty");
            if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
            if (age < 0) throw new IllegalArgumentException("Age cannot be negative");
            
            this.id = id;
            this.name = name;
            this.age = age;
            this.department = department;
            this.employmentStatus = employmentStatus;
            this.isIdValid = isIdValid;
            this.securityClearanceLevel = securityClearanceLevel;
        }

        public String getId() { return id; }
        public String getName() { return name; }
        public int getAge() { return age; }
        public String getDepartment() { return department; }
        public String getEmploymentStatus() { return employmentStatus; }
        public boolean isIdValid() { return isIdValid; }
        public int getSecurityClearanceLevel() { return securityClearanceLevel; }
    }

    
    public static class EvaluationResult {
        private String status; 
        private List<String> rejectionReasons = new ArrayList<>();

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public List<String> getRejectionReasons() { return rejectionReasons; }
        public void addReason(String reason) { this.rejectionReasons.add(reason); }
    }

   
    public static class AccessEvaluator {
        private static final List<String> AUTHORIZED_DEPTS = Arrays.asList("IT", "HR", "Finance", "Administration");

        public static EvaluationResult evaluateAccess(Employee emp, int requestedAccessLevel) {
            EvaluationResult result = new EvaluationResult();
            
            
            if (emp.getAge() < 21) {
                result.addReason("Employee age is under 21.");
            }
            if (emp.getDepartment() == null || !AUTHORIZED_DEPTS.contains(emp.getDepartment())) {
                result.addReason("Department '" + emp.getDepartment() + "' is not authorized.");
            }
            if (!"Active".equalsIgnoreCase(emp.getEmploymentStatus())) {
                result.addReason("Employment status is inactive.");
            }
            if (!emp.isIdValid()) {
                result.addReason("Employee ID is invalid.");
            }

            
            if (!result.getRejectionReasons().isEmpty()) {
                result.setStatus("Not Eligible");
            } else if (emp.getSecurityClearanceLevel() < requestedAccessLevel) {
                result.setStatus("Conditionally Eligible");
                result.addReason("Insufficient security clearance level.");
            } else {
                result.setStatus("Eligible");
            }

            return result;
        }
    }

    
    public static void main(String[] args) {
        System.out.println("=== Automated Employee Access Verification Batch ===");

        List<Employee> employeeBatch = new ArrayList<>();
        
       
        try {
            employeeBatch.add(new Employee("E001", "Alice Jenkins", 25, "IT", "Active", true, 4));
            employeeBatch.add(new Employee("E002", "Bob Smith", 21, "HR", "Active", true, 2));
            employeeBatch.add(new Employee("E003", "Charlie Brown", 30, "Finance", "Active", true, 1));
            employeeBatch.add(new Employee("E004", "David Miller", 19, "Marketing", "Inactive", false, 1));
        } catch (IllegalArgumentException e) {
            System.out.println("[Runtime Exception] Initialization Failed: " + e.getMessage());
            return;
        }

        int targetResourceLevel = 3;

        for (Employee emp : employeeBatch) {
            EvaluationResult result = AccessEvaluator.evaluateAccess(emp, targetResourceLevel);

            System.out.println("\n-------------------------------------------");
            System.out.println("ID     : " + emp.getId());
            System.out.println("Name   : " + emp.getName());
            System.out.println("Status : " + result.getStatus());
            
            if (!result.getRejectionReasons().isEmpty()) {
                System.out.println("Reasons Met:");
                for (String reason : result.getRejectionReasons()) {
                    System.out.println("  - " + reason);
                }
            } else {
                System.out.println("Access Permitted.");
            }
        }
        System.out.println("\n-------------------------------------------");
    }
}
