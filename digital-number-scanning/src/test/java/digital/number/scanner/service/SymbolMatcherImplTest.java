package digital.number.scanner.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SymbolMatcherImplTest {
    private final SymbolMatcherImpl symbolMatcher = new SymbolMatcherImpl();
    
    @Test
    public void matchValidSymbols() {
        assertEquals('0', symbolMatcher.matchSymbol(0, new Chunk(
                " _ ",
                "| |",
                "|_|")));
        assertEquals('1', symbolMatcher.matchSymbol(0, new Chunk(
                "   ",
                "  |",
                "  |")));
        assertEquals('2', symbolMatcher.matchSymbol(0, new Chunk(
                " _ ",
                " _|",
                "|_ ")));
        assertEquals('3', symbolMatcher.matchSymbol(0, new Chunk(
                " _ ",
                " _|",
                " _|")));
        assertEquals('4', symbolMatcher.matchSymbol(0, new Chunk(
                "   ",
                "|_|",
                "  |")));
        assertEquals('5', symbolMatcher.matchSymbol(0, new Chunk(
                " _ ",
                "|_ ",
                " _|")));
        assertEquals('6', symbolMatcher.matchSymbol(0, new Chunk(
                " _ ",
                "|_ ",
                "|_|")));
        assertEquals('7', symbolMatcher.matchSymbol(0, new Chunk(
                " _ ",
                "  |",
                "  |")));
        assertEquals('8', symbolMatcher.matchSymbol(0, new Chunk(
                " _ ",
                "|_|",
                "|_|")));
        assertEquals('9', symbolMatcher.matchSymbol(0, new Chunk(
                " _ ",
                "|_|",
                " _|")));
    }

    @Test
    public void inValidSymbols() throws Exception {
        assertEquals('?', symbolMatcher.matchSymbol(0, new Chunk(
                "   ",
                "  |",
                "   ")));
        assertEquals('?', symbolMatcher.matchSymbol(0, new Chunk(
                " * ",
                "  |",
                "  |")));
        assertEquals('?', symbolMatcher.matchSymbol(0, new Chunk(
                "  ",
                "  |",
                "  |")));
        assertEquals('?', symbolMatcher.matchSymbol(0, new Chunk(
                null,
                "..|",
                "..|")));
        assertEquals('?', symbolMatcher.matchSymbol(3, new Chunk(
                " _ ",
                "|_|",
                " _|")));
    }
}
