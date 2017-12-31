package org.laxio.piston.ignition;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.laxio.piston.ignition.console.AphelionCommandHandler;
import org.laxio.piston.ignition.console.CommandReader;
import org.laxio.piston.piston.ServerManager;
import org.laxio.piston.piston.chat.ChatColor;
import org.laxio.piston.piston.util.Environment;
import org.laxio.piston.protocol.v340.StickyProtocolV340;
import org.laxio.piston.protocol.v340.netty.NetworkServer;
import org.laxio.piston.sticky.StickyInitiator;
import org.laxio.piston.sticky.StickyPistonServer;
import org.laxio.piston.sticky.logging.ConsoleFormatter;
import org.laxio.piston.sticky.logging.LogUtil;

import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PistonIgnition {

    public static void main(String[] args) throws Exception {
        StickyInitiator.setup();

        /*
        for (PistonModuleType type : PistonModuleType.values()) {
            PistonModule module = type.getModule();
            System.out.println(module.getTitle() + ": " + module.getVersion());
        }
        */

        if (!Environment.isDebugMode()) {
            Logger.getGlobal().setLevel(Level.INFO);
        }

        System.out.print(ChatColor.RESET.toConsole());
        ConsoleHandler console = new ConsoleHandler();
        console.setLevel(Logger.getGlobal().getLevel());
        console.setFormatter(new ConsoleFormatter());
        LogUtil.init(console);

        StickyPistonServer serv = null;
        for (int i = 0; i < 5; i++) {
            StickyPistonServer server = new StickyPistonServer(new StickyProtocolV340(), "MC" + String.format("%03d", i + 1));
            NetworkServer network = new NetworkServer(server, new InetSocketAddress("0.0.0.0", 25565 + i));
            server.setNetwork(network);
            server.start();

            ServerManager.getInstance().addServer(server);

            if (serv == null) {
                serv = server;
            }
        }

        // Thread.sleep(Integer.MAX_VALUE);

        TerminalBuilder builder = TerminalBuilder.builder();
        builder.jansi(true);
        builder.dumb(true);

        Terminal terminal = builder.build();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        AphelionCommandHandler handler = new AphelionCommandHandler(serv);
        CommandReader command = new CommandReader(reader, handler);
        command.run();
    }

}
