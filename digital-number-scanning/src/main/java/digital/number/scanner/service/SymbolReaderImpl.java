package digital.number.scanner.service;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SymbolReaderImpl implements SymbolReader {
    private final SymbolMatcher symbolMatcher;

    public SymbolReaderImpl(SymbolMatcher symbolMatcher) {
        this.symbolMatcher = symbolMatcher;
    }

    public Stream<Character> readSymbols(Chunk chunk) {
        return IntStream.range(0, 9).mapToObj(i -> symbolMatcher.matchSymbol(i * 3, chunk));
    }
}
