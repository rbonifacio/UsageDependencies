package br.unb.cic.sa.export;

import br.unb.cic.sa.model.Dependency;
import br.unb.cic.sa.model.MethodDeclaration;
import br.unb.cic.sa.model.TypeDeclaration;

import java.io.PrintStream;
import java.util.Collection;
import java.util.stream.Collectors;

public class MDGExporter extends AbstractExporter {

    public MDGExporter(PrintStream out) {
        super(out);
    }

    @Override
    public void export(Collection<TypeDeclaration> decs) {
        head();
        declarations(decs);
        tail();
    }

    private void head() {
        out.println("graph MDG {");
    }

    private void declarations(Collection<TypeDeclaration> decs) {
        for (TypeDeclaration d: decs) {
            out.println("// " + d.getName());
            for(MethodDeclaration m : d.getMethods()) {
                if(m.getDependencies() != null) {
                    m.getDependencies().stream().forEach(dep -> {
                        exportMethodDependency(m, dep);
                    });
                }
            }
        }

    }

    private void exportMethodDependency(MethodDeclaration m, Dependency dep) {
        out.println("   " + m.signature() + " --" + dep.getMemberName().replace('<',' ').replace('>', ' '));
    }

    private void tail() {
        out.println("}");
    }
}
