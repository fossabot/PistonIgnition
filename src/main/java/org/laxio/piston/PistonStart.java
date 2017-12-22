package org.laxio.piston;

import org.laxio.piston.protocol.v001.StickyProtocol;
import org.laxio.piston.sticky.StickyPistonServer;

public class PistonStart {

    public static void main(String[] args) {
        StickyProtocol protocol = new StickyProtocol();
        StickyPistonServer server = new StickyPistonServer(protocol);
    }

}
