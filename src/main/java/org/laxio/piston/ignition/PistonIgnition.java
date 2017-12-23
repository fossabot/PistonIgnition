package org.laxio.piston.ignition;

import org.json.JSONObject;
import org.laxio.piston.piston.PistonServer;
import org.laxio.piston.protocol.v001.StickyProtocol;
import org.laxio.piston.sticky.StickyPistonServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class PistonIgnition {

    public static void main(String[] args) throws IOException {
        StickyProtocol protocol = new StickyProtocol();
        StickyPistonServer server = new StickyPistonServer(protocol);

        test(PistonIgnition.class, "Piston Ignition");
        System.out.println();
        test(PistonServer.class, "Piston API");
        System.out.println();
        test(StickyPistonServer.class, "Sticky Piston");
        System.out.println();
        test(StickyProtocol.class, "Piston Protocol");
    }

    public static void test(Class<?> cls, String name) throws IOException {
        System.out.println(name + ":");

        String jsonName = "/" + cls.getPackage().getName() + ".json";
        InputStream stream = StickyProtocol.class.getResourceAsStream(jsonName);
        JSONObject json = new JSONObject(read(stream));

        // System.out.println(json);
        System.out.println("Package: " + cls.getPackage().getName());

        /*
        System.out.println("Specification Title: " + cls.getPackage().getSpecificationTitle());
        System.out.println("Specification Version: " + cls.getPackage().getSpecificationVersion());
        System.out.println("Specification Vendor: " + cls.getPackage().getSpecificationVendor());
        System.out.println("Implementation Title: " + cls.getPackage().getImplementationTitle());
        System.out.println("Implementation Version: " + cls.getPackage().getImplementationVersion());
        System.out.println("Implementation Vendor: " + cls.getPackage().getImplementationVendor());
         */

        out(json, "Specification Title", "Specification-Title");
        out(json, "Specification Version", "Specification-Version");
        out(json, "Specification Vendor", "Specification-Vendor");
        out(json, "Implementation Title", "Implementation-Title");
        out(json, "Implementation Version", "Implementation-Version");
        out(json, "Implementation Vendor", "Implementation-Vendor");
    }

    public static void out(JSONObject json, String name, String key) {
        if (!json.has(key)) {
            return;
        }

        System.out.println(name + ": " + json.getString(key));
    }

    public static String read(InputStream input) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

}
