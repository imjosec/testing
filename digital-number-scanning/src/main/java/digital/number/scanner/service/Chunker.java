package digital.number.scanner.service;

import java.util.stream.Stream;

public interface Chunker {
    Stream<Chunk> chunk(final Stream<String> lines);
}
