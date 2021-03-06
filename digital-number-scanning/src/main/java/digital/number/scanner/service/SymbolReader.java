package digital.number.scanner.service;

import java.util.stream.Stream;

public interface SymbolReader {
    Stream<Character> readSymbols(Chunk chunk);
}
