package digital.number.scanner.service;

import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class OutputWriterImplTest {
    private StringBuilder stringBuilder = new StringBuilder();
    private OutputWriterImpl outputWriter = new OutputWriterImpl(c -> stringBuilder.append(c));

    @Before
    public void setup() {
        stringBuilder.setLength(0);
    }

    @Test
    public void validOutput() {
        String test = "123456789";
        outputWriter.writeOutput(test.chars().mapToObj(i -> (char)i));
        assertEquals(test, stringBuilder.toString());
    }

    @Test
    public void invalidOutput() {
        String test = "1234?6789";
        outputWriter.writeOutput(test.chars().mapToObj(i -> (char)i));
        assertEquals(test + "ILL", stringBuilder.toString());
    }
}