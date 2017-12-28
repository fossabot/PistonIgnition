package org.laxio.piston.ignition.console;

import org.laxio.piston.sticky.StickyPistonServer;
import org.laxio.piston.sticky.logging.CommandHandler;

public class AphelionCommandHandler implements CommandHandler {

    private final StickyPistonServer server;

    public AphelionCommandHandler(StickyPistonServer server) {
        this.server = server;
    }

    @Override
    public void handle(String string) {
        this.server.getAphelionHandler().handle(server.getConsole(), string);
    }

}
