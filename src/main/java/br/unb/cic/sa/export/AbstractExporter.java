package br.unb.cic.sa.export;

import br.unb.cic.sa.model.TypeDeclaration;

import java.io.PrintStream;
import java.util.Collection;

/**
 * Declares the hierarchy for dependency
 * exporters.
 */
public abstract class AbstractExporter {
    protected PrintStream out;

    public AbstractExporter(PrintStream out) {
        this.out = out;
    }

    public abstract void export(Collection<TypeDeclaration> decs);
}
