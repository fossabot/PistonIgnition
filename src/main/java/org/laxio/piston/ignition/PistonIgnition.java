package org.laxio.piston.ignition;

import org.laxio.piston.piston.versioning.PistonModule;
import org.laxio.piston.piston.versioning.PistonModuleType;
import org.laxio.piston.sticky.StickyInitiator;

import java.io.IOException;

public class PistonIgnition {

    public static void main(String[] args) throws IOException {
        StickyInitiator.setup();

        for (PistonModuleType type : PistonModuleType.values()) {
            PistonModule module = type.getModule();
            System.out.println(module.getTitle() + ": " + module.getVersion());
        }
    }

}
