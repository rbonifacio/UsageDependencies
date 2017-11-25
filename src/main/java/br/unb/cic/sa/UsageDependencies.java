package br.unb.cic.sa;

import br.unb.cic.sa.export.Factory;
import br.unb.cic.sa.model.Dependency;
import br.unb.cic.sa.model.DependencyType;
import br.unb.cic.sa.model.MethodDeclaration;
import br.unb.cic.sa.model.TypeDeclaration;
import soot.JastAddJ.TypeDecl;
import soot.jimple.FieldRef;
import soot.jimple.internal.JInstanceFieldRef;
import soot.jimple.internal.JInvokeStmt;
import soot.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * The main application of the project.
 * It basically drives the use of SOOT in order
 * to compute the usage dependencies of the source code.
 */
public class UsageDependencies {

    private ConcurrentHashMap<String, TypeDeclaration> decls = new ConcurrentHashMap<>();

    //private HashMap<String, TypeDeclaration> decls = new HashMap<>();

    public static void main(String args[]) {
        UsageDependencies analyser = new UsageDependencies();

        System.out.println("-----------------------------");
        System.out.println(" Building usage dependencies ");
        System.out.println("-----------------------------");

        PackManager.v().getPack("jtp").add(
                new Transform("jtp.methodDependencies", new BodyTransformer() {
                    @Override
                    protected void internalTransform(Body body, String s, Map<String, String> map) {
                        SootClass  c = body.getMethod().getDeclaringClass();
                        SootMethod m = body.getMethod();

                        TypeDeclaration decl = null;

                        System.out.println("Computing dependencies for method: " + body.getMethod().getSignature());


                        if(!analyser.decls.keySet().contains(c.getName())) {
                            decl = TypeDeclaration.builder().name(c.getName()).build();
                            analyser.decls.put(c.getName(), decl);
                        }

                        decl = analyser.decls.get(c.getName());

                        decl.addMethod(getMethodDeclarationDependencies(m, body.getUnits()));

                        analyser.decls.put(decl.getName(), decl);
                    }
                })
        );

        soot.Main.main(args);

        Factory.singleton().getExporter("mdg").export(analyser.decls.values());
    }

    private static MethodDeclaration getMethodDeclarationDependencies(SootMethod m, Collection<Unit> unities) {
        MethodDeclaration md =
                MethodDeclaration.builder()
                .returnType(m.getReturnType().toString())
                .name(m.getName())
                .pmts(m.getParameterTypes().stream().map(t -> t.toString()).collect(Collectors.toList()))
                .build();


        for(Unit unit: unities) {
            if(unit instanceof JInvokeStmt) {
                JInvokeStmt invokeStmt   = (JInvokeStmt)unit;
                SootMethod  invokeMethod = invokeStmt.getInvokeExpr().getMethod();
                Dependency dependency   = Dependency.builder()
                        .typeName(invokeMethod.getDeclaringClass().toString())
                        .memberName(invokeMethod.getSignature())
                        .type(DependencyType.MethodCall)
                        .build();

                md.addDependency(dependency);
            }
            else if(unit instanceof JInstanceFieldRef) {
                JInstanceFieldRef fieldRef = (JInstanceFieldRef)unit;
                Dependency dependency = Dependency.builder()
                        .typeName(fieldRef.getField().getDeclaringClass().toString())
                        .memberName(fieldRef.getField().getName())
                        .type(DependencyType.FieldReference)
                        .build();

                md.addDependency(dependency);
            }
        }
        return md;
    }


}
