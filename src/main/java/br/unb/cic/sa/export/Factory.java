package br.unb.cic.sa.export;

import java.io.PrintStream;

/**
 * A singleton factory for exporters.
 */
public class Factory {

    private static Factory singleton;
    private Factory() {}

    public static Factory singleton() {
        if(singleton == null) {
            singleton = new Factory();
        }
        return singleton;
    }

    public AbstractExporter getDefault() {
        return new DefaultExporter(new PrintStream(System.out));
    }

    public AbstractExporter getExporter(String e) {
        switch(e) {
            case "default" : return new DefaultExporter(new PrintStream(System.out));
            case "mdg" : return new MDGExporter(new PrintStream(System.out));
            default: return new DefaultExporter(new PrintStream(System.out));
        }
    }
}
