package digital.number.scanner.service;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class ChunkerImplTest {
    private ChunkerImpl chunker = new ChunkerImpl();

    @Test
    public void test1() {
        List<String> list = asList("string1", "string2", "string3", "");
        Stream<Chunk> chunkStream = chunker.chunk(list.stream());
        List<Chunk> chunkList = chunkStream.collect(toList());
        assertEquals(1, chunkList.size());
        assertEquals(list.get(0), chunkList.get(0).getCs1());
        assertEquals(list.get(1), chunkList.get(0).getCs2());
        assertEquals(list.get(2), chunkList.get(0).getCs3());
    }

    @Test
    public void test2() {
        List<String> list = asList(
                "string1", "string2", "string3", "",
                "string4", "string5", "string6", "");
        Stream<Chunk> chunkStream = chunker.chunk(list.stream());
        List<Chunk> chunkList = chunkStream.collect(toList());
        assertEquals(2, chunkList.size());
        assertEquals(list.get(0), chunkList.get(0).getCs1());
        assertEquals(list.get(1), chunkList.get(0).getCs2());
        assertEquals(list.get(2), chunkList.get(0).getCs3());
        assertEquals(list.get(4), chunkList.get(1).getCs1());
        assertEquals(list.get(5), chunkList.get(1).getCs2());
        assertEquals(list.get(6), chunkList.get(1).getCs3());
    }

}