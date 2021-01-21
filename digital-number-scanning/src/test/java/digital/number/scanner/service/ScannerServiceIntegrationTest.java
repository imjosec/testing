package digital.number.scanner.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class ScannerServiceIntegrationTest extends BaseScannerServiceIntegrationTest {
    private List<String> output = new ArrayList<>();

    @Override
    protected List<String> performScanning(String inputFilePath) {
        List<String> output = new ArrayList<>();
        Processor processor = new Processor(output::add);
        try {
            processor.process(inputFilePath);
        } catch (IOException e) {
            fail("Unexpected Exception");
            e.printStackTrace();
        }
        return output;
    }
}
