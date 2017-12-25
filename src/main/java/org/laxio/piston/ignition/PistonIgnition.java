package org.laxio.piston.ignition;

import org.laxio.piston.piston.versioning.PistonModule;
import org.laxio.piston.piston.versioning.PistonModuleType;
import org.laxio.piston.protocol.v001.netty.NetworkServer;
import org.laxio.piston.sticky.StickyInitiator;

import java.io.IOException;
import java.net.InetSocketAddress;

public class PistonIgnition {

    public static void main(String[] args) throws Exception {
        StickyInitiator.setup();

        for (PistonModuleType type : PistonModuleType.values()) {
            PistonModule module = type.getModule();
            System.out.println(module.getTitle() + ": " + module.getVersion());
        }

        new NetworkServer(new InetSocketAddress("0.0.0.0", 25565)).run();
        Thread.sleep(Integer.MAX_VALUE);
    }

}
