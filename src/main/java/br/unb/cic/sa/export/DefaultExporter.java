package br.unb.cic.sa.export;

import br.unb.cic.sa.model.TypeDeclaration;

import java.io.PrintStream;
import java.util.Collection;

/**
 * The default exporter. It mainly prints the
 * type declarations and dependencies in plain text.
 */
public class DefaultExporter extends AbstractExporter {
    public DefaultExporter(PrintStream out) {
        super(out);
    }

    public void export(Collection<TypeDeclaration> decls) {
        decls.stream().forEach(dec -> {
            out.println(dec.getName());
            dec.getMethods().stream().forEach(m -> {
                out.println("-" + m.signature());
                if(m.getDependencies() != null) {
                    m.getDependencies().stream().forEach(d -> {
                        out.println("  (dep): " + d.getMemberName());
                    });
                    out.println();
                }
            });
        });
    }
}
