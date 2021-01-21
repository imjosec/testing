package digital.number.scanner.service;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileParser {
    Stream<String> parseFile(String fileName) throws IOException;
}
