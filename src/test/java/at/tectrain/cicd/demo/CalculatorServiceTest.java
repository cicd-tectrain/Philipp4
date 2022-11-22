package at.tectrain.cicd.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceTest {

    private CalculatorService service = new CalculatorService();

    @Test
    void testAdd() {
        int result = service.add(1, 2);
        assertEquals(3, result);
    }

    @Test
    void testSubtract() {
        int result = service.subtract(5, 2);
        assertEquals(3, result);
    }

    @Test
    void testMultiply() {
        int result = service.multiply(2, 5);
        assertEquals(10, result);
    }

    @Test
    void testDivide() {
        int result = service.divide(15, 3);
        assertEquals(5, result);
    }

}
