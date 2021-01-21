package digital.number.scanner.service;

public interface SymbolMatcher {
    char matchSymbol(int start, Chunk c);
}
