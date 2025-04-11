package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OperationsTest {
    
    @Test
    public void testGetOperator() {
        // Test all enum values
        assertEquals("==", Operations.EQUALS.getOperator());
        assertEquals("!=", Operations.NOT_EQUALS.getOperator());
        assertEquals(">", Operations.GREATER_THAN.getOperator());
        assertEquals("<", Operations.LESS_THAN.getOperator());
        assertEquals(">=", Operations.GREATER_THAN_EQUALS.getOperator());
        assertEquals("<=", Operations.LESS_THAN_EQUALS.getOperator());
        assertEquals("~=", Operations.CONTAINS.getOperator());
    }
    
    @Test
    public void testFromOperator_ValidOperators() {
        // Test all operators
        assertEquals(Operations.EQUALS, Operations.fromOperator("=="));
        assertEquals(Operations.NOT_EQUALS, Operations.fromOperator("!="));
        assertEquals(Operations.GREATER_THAN, Operations.fromOperator(">"));
        assertEquals(Operations.LESS_THAN, Operations.fromOperator("<"));
        assertEquals(Operations.GREATER_THAN_EQUALS, Operations.fromOperator(">="));
        assertEquals(Operations.LESS_THAN_EQUALS, Operations.fromOperator("<="));
        assertEquals(Operations.CONTAINS, Operations.fromOperator("~="));
    }
    
    @Test
    public void testFromOperator_InvalidOperator() {
        assertThrows(IllegalArgumentException.class, () -> {
            Operations.fromOperator("invalid");
        });
    }
    
    @Test
    public void testGetOperatorFromStr_ExactMatches() {
        // Test exact matches for all operators
        assertEquals(Operations.EQUALS, Operations.getOperatorFromStr("=="));
        assertEquals(Operations.NOT_EQUALS, Operations.getOperatorFromStr("!="));
        assertEquals(Operations.GREATER_THAN, Operations.getOperatorFromStr(">"));
        assertEquals(Operations.LESS_THAN, Operations.getOperatorFromStr("<"));
        assertEquals(Operations.GREATER_THAN_EQUALS, Operations.getOperatorFromStr(">="));
        assertEquals(Operations.LESS_THAN_EQUALS, Operations.getOperatorFromStr("<="));
        assertEquals(Operations.CONTAINS, Operations.getOperatorFromStr("~="));
    }
    
    @Test
    public void testGetOperatorFromStr_ContainingOperators() {
        // Test strings containing operators
        assertEquals(Operations.EQUALS, Operations.getOperatorFromStr("value==10"));
        assertEquals(Operations.NOT_EQUALS, Operations.getOperatorFromStr("value!=10"));
        assertEquals(Operations.GREATER_THAN, Operations.getOperatorFromStr("value>10"));
        assertEquals(Operations.LESS_THAN, Operations.getOperatorFromStr("value<10"));
        assertEquals(Operations.GREATER_THAN_EQUALS, Operations.getOperatorFromStr("value>=10"));
        assertEquals(Operations.LESS_THAN_EQUALS, Operations.getOperatorFromStr("value<=10"));
        assertEquals(Operations.CONTAINS, Operations.getOperatorFromStr("name~=Smith"));
    }
    
    @Test
    public void testGetOperatorFromStr_WithSpaces() {
        // Test with spaces
        assertEquals(Operations.EQUALS, Operations.getOperatorFromStr("value == 5"));
        assertEquals(Operations.NOT_EQUALS, Operations.getOperatorFromStr("value != 5"));
    }
    
    @Test
    public void testGetOperatorFromStr_NoMatch() {
        // Test no match
        assertNull(Operations.getOperatorFromStr(""));
        assertNull(Operations.getOperatorFromStr("valuewithnooperator"));
    }
    
    @Test
    public void testGetOperatorFromStr_PrecedenceOrder() {
        // Tests that operators like >= are correctly identified before >
        assertEquals(Operations.GREATER_THAN_EQUALS, Operations.getOperatorFromStr("value>=5"));
        assertEquals(Operations.LESS_THAN_EQUALS, Operations.getOperatorFromStr("value<=5"));
    }
}
