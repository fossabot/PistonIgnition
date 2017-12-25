package org.laxio.piston.ignition;

import org.laxio.piston.piston.versioning.PistonModule;
import org.laxio.piston.piston.versioning.PistonModuleType;
import org.laxio.piston.protocol.v340.StickyProtocolV340;
import org.laxio.piston.protocol.v340.netty.NetworkServer;
import org.laxio.piston.sticky.StickyInitiator;
import org.laxio.piston.sticky.StickyPistonServer;

import java.net.InetSocketAddress;

public class PistonIgnition {

    public static void main(String[] args) throws Exception {
        StickyInitiator.setup();

        for (PistonModuleType type : PistonModuleType.values()) {
            PistonModule module = type.getModule();
            System.out.println(module.getTitle() + ": " + module.getVersion());
        }

        StickyPistonServer server = new StickyPistonServer(new StickyProtocolV340());
        new NetworkServer(server, new InetSocketAddress("0.0.0.0", 25565)).run();
        Thread.sleep(Integer.MAX_VALUE);
    }

}
