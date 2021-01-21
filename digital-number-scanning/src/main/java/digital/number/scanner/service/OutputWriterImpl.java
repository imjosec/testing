package digital.number.scanner.service;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class OutputWriterImpl implements OutputWriter {
    private final Consumer<String> output;

    public OutputWriterImpl(Consumer<String> output) {
        this.output = output;
    }

    public void writeOutput(Stream<Character> str) {
        final StringBuilder sb = new StringBuilder();
        str.forEach(sb::append);
        if(sb.indexOf("?") >= 0) {
            sb.append("ILL");
        }
        output.accept(sb.toString());
    }
}
