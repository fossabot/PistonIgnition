package org.laxio.piston.ignition.console;

import org.jline.reader.LineReader;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class ConsoleLogHandler extends ConsoleHandler {

    private LineReader reader;

    public ConsoleLogHandler(LineReader reader, Formatter formatter) {
        this.reader = reader;
        this.setFormatter(formatter);
    }

    public void publish(LogRecord record) {
        super.publish(record);
    }

    public synchronized void flush() {}

}
