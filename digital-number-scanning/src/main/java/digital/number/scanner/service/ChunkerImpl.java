package digital.number.scanner.service;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ChunkerImpl implements Chunker {
    public Stream<Chunk> chunk(final Stream<String> lines) {
        final Iterator<String> iterator = lines.iterator();
        final Iterator<Chunk> chunkIterator = new Iterator<Chunk>() {
            public boolean hasNext() {
                return iterator.hasNext();
            }

            public Chunk next() {
                String[] strs = new String[3];
                for(int i = 0; i < 3 && iterator.hasNext(); i++) {
                    strs[i] = iterator.next();
                }
                if(iterator.hasNext())
                    iterator.next();
                return new Chunk(strs[0], strs[1], strs[2]);
            }
        };
        return StreamSupport.stream(((Iterable<Chunk>)() -> chunkIterator).spliterator(), false);
    }
}
