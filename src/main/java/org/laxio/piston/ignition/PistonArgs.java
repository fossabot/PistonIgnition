package org.laxio.piston.ignition;

import com.beust.jcommander.Parameter;

public class PistonArgs {

    @Parameter(names = "--debug", description = "Debug mode")
    boolean debug = false;

    @Parameter(names = "--single", description = "Run a single instance")
    boolean single = false;

    @Parameter(names = {"--instances", "-i"}, description = "Number of instances to run")
    int instances = 1;

}
