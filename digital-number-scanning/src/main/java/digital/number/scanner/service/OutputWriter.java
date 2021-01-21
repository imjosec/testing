package digital.number.scanner.service;

import java.util.stream.Stream;

public interface OutputWriter {
    void writeOutput(Stream<Character> str);
}
