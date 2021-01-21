package digital.number.scanner.service;

import java.io.IOException;
import java.util.function.Consumer;

public class Processor {
    private final FileParser fileParser;
    private final Chunker chunker;
    private final SymbolMatcher symbolMatcher;
    private final SymbolReader symbolReader;
    private final OutputWriter outputWriter;

    public Processor() {
        this(System.out::println);
    }

    public Processor(Consumer<String> output) {
        fileParser = new FileParserImpl();
        chunker = new ChunkerImpl();
        symbolMatcher = new SymbolMatcherImpl();
        symbolReader = new SymbolReaderImpl(symbolMatcher);
        outputWriter = new OutputWriterImpl(output);
    }

    public void process(String fileName) throws IOException {
        chunker.chunk(fileParser.parseFile(fileName))
               .forEach(c -> outputWriter.writeOutput(symbolReader.readSymbols(c)));
    }
}
