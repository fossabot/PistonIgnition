package org.laxio.piston.ignition.console;

import org.jline.reader.LineReader;
import org.laxio.piston.sticky.logging.CommandHandler;

public class CommandReader extends Thread {

    private LineReader reader;
    private CommandHandler handler;

    public CommandReader(LineReader reader, CommandHandler handler) {
        this.reader = reader;
        this.handler = handler;
    }

    public CommandHandler getHandler() {
        return handler;
    }

    public void setHandler(CommandHandler handler) {
        this.handler = handler;
    }

    public void run() {
        String string;
        while ((string = reader.readLine()) != null) {
            if (handler != null) {
                handler.handle(string);
            }
        }
    }

}
