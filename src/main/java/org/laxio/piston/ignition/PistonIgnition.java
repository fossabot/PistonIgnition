package org.laxio.piston.ignition;

        /*-
         * #%L
         * Piston Ignition
         * %%
         * Copyright (C) 2017 - 2018 Laxio
         * %%
         * This file is part of Piston, licensed under the MIT License (MIT).
         *
         * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
         *
         * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
         *
         * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
         * #L%
         */

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
