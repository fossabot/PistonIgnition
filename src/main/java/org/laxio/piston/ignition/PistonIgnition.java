package org.laxio.piston.ignition;

import com.beust.jcommander.JCommander;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.laxio.piston.ignition.console.AphelionConsoleHandler;
import org.laxio.piston.ignition.console.AphelionManagerConsoleHandler;
import org.laxio.piston.ignition.console.CommandReader;
import org.laxio.piston.piston.ServerManager;
import org.laxio.piston.piston.chat.ChatColor;
import org.laxio.piston.piston.util.Environment;
import org.laxio.piston.protocol.v340.StickyProtocolV340;
import org.laxio.piston.protocol.v340.netty.NetworkServer;
import org.laxio.piston.protocol.v340.util.ServerShutdown;
import org.laxio.piston.sticky.StickyInitiator;
import org.laxio.piston.sticky.StickyPistonServer;
import org.laxio.piston.sticky.logging.CommandHandler;
import org.laxio.piston.sticky.logging.ConsoleFormatter;
import org.laxio.piston.sticky.logging.LogUtil;

import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PistonIgnition {

    public static void main(String[] args) throws Exception {
        StickyInitiator.setup();
        Runtime.getRuntime().addShutdownHook(new ServerShutdown());

        PistonArgs commander = new PistonArgs();
        JCommander.newBuilder()
                .addObject(commander)
                .build()
                .parse(args);

        Environment.setDebugMode(commander.debug);

        if (!Environment.isDebugMode()) {
            Logger.getGlobal().setLevel(Level.INFO);
        }

        System.out.print(ChatColor.RESET.toConsole());
        ConsoleHandler console = new ConsoleHandler();
        console.setLevel(Logger.getGlobal().getLevel());
        console.setFormatter(new ConsoleFormatter());
        LogUtil.init(console);

        ServerManager manager = ServerManager.getInstance();
        int instances = commander.single ? 1 : commander.instances;

        StickyPistonServer serv = null;
        for (int i = 0; i < instances; i++) {
            StickyPistonServer server = new StickyPistonServer(new StickyProtocolV340(), (commander.single ? null : "MC" + String.format("%03d", i + 1)));
            NetworkServer network = new NetworkServer(server, new InetSocketAddress("0.0.0.0", 25565 + i));
            server.setNetwork(network);
            server.start();

            manager.addServer(server);

            if (serv == null) {
                serv = server;
            }
        }

        TerminalBuilder builder = TerminalBuilder.builder();
        builder.jansi(true);
        builder.dumb(true);

        Terminal terminal = builder.build();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        CommandHandler handler;
        if (commander.single) {
            handler = new AphelionConsoleHandler(serv);
        } else {
            handler = new AphelionManagerConsoleHandler();
        }

        CommandReader command = new CommandReader(reader, handler);
        command.run();
    }

}
