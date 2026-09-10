package com.example;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class AppTest {

    @Test
    public void testNormalScenario_Eligible() {
        App.Employee emp = new App.Employee("E001", "Alice", 25, "IT", "Active", true, 3);
        App.EvaluationResult result = App.AccessEvaluator.evaluateAccess(emp, 3);
        assertEquals("Eligible", result.getStatus());
        assertTrue(result.getRejectionReasons().isEmpty());
    }

    @Test
    public void testBoundaryScenario_AgeExactly21() {
        App.Employee emp = new App.Employee("E002", "Bob", 21, "HR", "Active", true, 2);
        App.EvaluationResult result = App.AccessEvaluator.evaluateAccess(emp, 2);
        assertEquals("Eligible", result.getStatus());
    }

    @Test
    public void testConditionallyEligible_LowClearance() {
        App.Employee emp = new App.Employee("E003", "Charlie", 30, "Finance", "Active", true, 2);
        App.EvaluationResult result = App.AccessEvaluator.evaluateAccess(emp, 4);
        assertEquals("Conditionally Eligible", result.getStatus());
        assertEquals(1, result.getRejectionReasons().size());
        assertEquals("Insufficient security clearance level.", result.getRejectionReasons().get(0));
    }

    @Test
    public void testMultipleFailuresScenario() {
        // Triggers all core restriction rules at once to verify non-halting error aggregation
        App.Employee emp = new App.Employee("E004", "Invalid Emp", 19, "Marketing", "Inactive", false, 1);
        App.EvaluationResult result = App.AccessEvaluator.evaluateAccess(emp, 2);

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
        // Enforces basic field input sanity testing
        new App.Employee("", "John", 25, "IT", "Active", true, 2);
    }
}
