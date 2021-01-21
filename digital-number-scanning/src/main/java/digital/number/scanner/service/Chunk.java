package digital.number.scanner.service;

public class Chunk {
    private final CharSequence cs1;
    private final CharSequence cs2;
    private final CharSequence cs3;

    public Chunk(CharSequence cs1, CharSequence cs2, CharSequence cs3) {
        this.cs1 = cs1;
        this.cs2 = cs2;
        this.cs3 = cs3;
    }

    public CharSequence getCs1() {
        return cs1;
    }

    public CharSequence getCs2() {
        return cs2;
    }

    public CharSequence getCs3() {
        return cs3;
    }
}
