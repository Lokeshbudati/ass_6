package com.example;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class AppTest {

    @Test
    public void testNormalScenario_Eligible() {
        Employee emp = new Employee("E001", "Alice", 25, "IT", "Active", true, 3);
        EvaluationResult result = AccessEvaluator.evaluateAccess(emp, 3);
        assertEquals("Eligible", result.getStatus());
        assertTrue(result.getRejectionReasons().isEmpty());
    }

    @Test
    public void testBoundaryScenario_AgeExactly21() {
        Employee emp = new Employee("E002", "Bob", 21, "HR", "Active", true, 2);
        EvaluationResult result = AccessEvaluator.evaluateAccess(emp, 2);
        assertEquals("Eligible", result.getStatus());
    }

    @Test
    public void testConditionallyEligible_LowClearance() {
        Employee emp = new Employee("E003", "Charlie", 30, "Finance", "Active", true, 2);
        EvaluationResult result = AccessEvaluator.evaluateAccess(emp, 4);
        assertEquals("Conditionally Eligible", result.getStatus());
        assertEquals(1, result.getRejectionReasons().size());
        assertEquals("Insufficient security clearance level.", result.getRejectionReasons().get(0));
    }

    @Test
    public void testMultipleFailuresScenario() {
        Employee emp = new Employee("E004", "Invalid Emp", 19, "Marketing", "Inactive", false, 1);
        EvaluationResult result = AccessEvaluator.evaluateAccess(emp, 2);

        assertEquals("Not Eligible", result.getStatus());
        List<String> reasons = result.getRejectionReasons();
        
        assertEquals(4, reasons.size());
        assertTrue(reasons.contains("Employee age is under 21."));
        assertTrue(reasons.contains("Department 'Marketing' is not authorized."));
        assertTrue(reasons.contains("Employment status is inactive."));
        assertTrue(reasons.contains("Employee ID is invalid."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInput_ThrowsException() {
        new Employee("", "John", 25, "IT", "Active", true, 2);
    }
}
