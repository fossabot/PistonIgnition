package org.laxio.piston.ignition;

import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.protocol.v001.StickyProtocol;
import org.laxio.piston.sticky.StickyPistonServer;

public class PistonIgnition {

    public static void main(String[] args) {
        StickyProtocol protocol = new StickyProtocol();
        StickyPistonServer server = new StickyPistonServer(protocol);

        test(PistonIgnition.class, "Piston Ignition");
        test(PistonServer.class, "Piston API");
        test(StickyPistonServer.class, "Sticky Piston");
        test(StickyProtocol.class, "Piston Protocol");
    }

    public static void test(Class<?> cls, String name) {
        System.out.println(name + ":");
        System.out.println("Packages: " + cls.getPackage().getName());
        System.out.println("Specification Title: " + cls.getPackage().getSpecificationTitle());
        System.out.println("Specification Version: " + cls.getPackage().getSpecificationVersion());
        System.out.println("Specification Vendor: " + cls.getPackage().getSpecificationVendor());
        System.out.println("Implementation Title: " + cls.getPackage().getImplementationTitle());
        System.out.println("Implementation Version: " + cls.getPackage().getImplementationVersion());
        System.out.println("Implementation Vendor: " + cls.getPackage().getImplementationVendor());
    }

}
