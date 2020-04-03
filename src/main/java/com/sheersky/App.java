package com.sheersky;

import picocli.CommandLine;

public class App {
    public static void main( String... args ) {
        try {
            int exitCode = new CommandLine(new Controller()).execute(args);
            System.exit(exitCode);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            System.exit(1);
        }
    }
}
