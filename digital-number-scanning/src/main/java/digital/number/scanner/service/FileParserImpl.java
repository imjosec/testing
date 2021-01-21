package digital.number.scanner.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileParserImpl implements FileParser {
    public Stream<String> parseFile(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName));
    }
}
