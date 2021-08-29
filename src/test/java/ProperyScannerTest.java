import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

class ProperyScannerTest {

    private ProperyScanner propertyScanner;

    @BeforeEach
    void setUp() throws IOException {
        propertyScanner = new ProperyScanner();
    }

    @Test
    @DisplayName("Property test: ")
    void testGetProperty() {
        String expected = "textToAssert";
        Assertions.assertEquals(expected, propertyScanner.getProperty("key"));
    }



}