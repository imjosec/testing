package digital.number.scanner.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SymbolReaderImplTest {
    @Mock
    private SymbolMatcher symbolMatcher;
    @InjectMocks
    private SymbolReaderImpl symbolReader;

    @Test
    public void test() {
        Chunk chunk = new Chunk(null, null, null);
        when(symbolMatcher.matchSymbol(anyInt(), same(chunk)))
                .thenAnswer(a -> (char)('0' + a.getArgumentAt(0, Integer.class)/3));
        Stream<Character> sc = symbolReader.readSymbols(chunk);
        String result = sc.map(Object::toString).collect(Collectors.joining());
        assertEquals("012345678", result);
    }

}