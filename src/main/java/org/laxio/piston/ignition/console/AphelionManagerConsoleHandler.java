package org.laxio.piston.ignition.console;

import org.laxio.piston.piston.ServerManager;
import org.laxio.piston.sticky.command.AphelionHandler;
import org.laxio.piston.sticky.logging.CommandHandler;

public class AphelionManagerConsoleHandler implements CommandHandler {

    private final ServerManager manager;
    private final AphelionHandler handler;

    public AphelionManagerConsoleHandler() {
        this.manager = ServerManager.getInstance();
        this.handler = new AphelionHandler(manager.getAphelion());
    }

    @Override
    public void handle(String string) {
        this.handler.handle(manager, string);
    }

}
