package digital.number.scanner.service;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class SymbolMatcherImpl implements SymbolMatcher {
    private static final Map<String, Character> CHARACTER_MAP = new HashMap<>();
    static {
        CHARACTER_MAP.put(" _ | ||_|", '0');
        CHARACTER_MAP.put("     |  |", '1');
        CHARACTER_MAP.put(" _  _||_ ", '2');
        CHARACTER_MAP.put(" _  _| _|", '3');
        CHARACTER_MAP.put("   |_|  |", '4');
        CHARACTER_MAP.put(" _ |_  _|", '5');
        CHARACTER_MAP.put(" _ |_ |_|", '6');
        CHARACTER_MAP.put(" _   |  |", '7');
        CHARACTER_MAP.put(" _ |_||_|", '8');
        CHARACTER_MAP.put(" _ |_| _|", '9');
    }

    public char matchSymbol(int start, Chunk c) {
        StringBuilder sb = new StringBuilder();
        if(start >= 0 && validCs(c.getCs1(), start, 3) && validCs(c.getCs2(), start, 3)
                && validCs(c.getCs3(), start, 3)) {
            addChars(sb, c.getCs1(), start, 3);
            addChars(sb, c.getCs2(), start, 3);
            addChars(sb, c.getCs3(), start, 3);
            return CHARACTER_MAP.getOrDefault(sb.toString(), '?');
        }
        return '?';
    }

    private static void addChars(StringBuilder sb, CharSequence cs, int start, int size) {
        for(int i=start; i<(start + size); i++) {
            sb.append(cs.charAt(i));
        }
    }

    private static boolean validCs(CharSequence cs, int start, int size) {
        return nonNull(cs) && cs.length() >= (start + size);
    }
}