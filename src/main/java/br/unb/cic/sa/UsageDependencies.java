package br.unb.cic.sa;

import br.unb.cic.sa.export.Factory;
import br.unb.cic.sa.model.Dependency;
import br.unb.cic.sa.model.MethodDeclaration;
import br.unb.cic.sa.model.TypeDeclaration;
import soot.JastAddJ.TypeDecl;
import soot.jimple.internal.JInvokeStmt;
import soot.*;
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

   // private ConcurrentHashMap<String, TypeDeclaration> decls = new ConcurrentHashMap<>();

    private HashMap<String, TypeDeclaration> decls = new HashMap<>();

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

                        if(decl == null) {
                            System.err.println("decl is null");
                        }

                        MethodDeclaration md =
                                MethodDeclaration.builder()
                                .returnType(m.getReturnType().toString())
                                .name(m.getName())
                                .pmts(m.getParameterTypes().stream().map(t -> t.toString()).collect(Collectors.toList()))
                                .build();


                        for(Unit unit: body.getUnits()) {
                            if(unit instanceof JInvokeStmt) {
                                JInvokeStmt invokeStmt   = (JInvokeStmt)unit;
                                SootMethod  invokeMethod = invokeStmt.getInvokeExpr().getMethod();
                                Dependency  dependency   = Dependency.builder()
                                        .typeName(invokeMethod.getDeclaringClass().toString())
                                        .memberName(invokeMethod.getSignature())
                                        .build();

                                md.addDependency(dependency);
                            }
                        }

                        decl.addMethod(md);

                        analyser.decls.put(decl.getName(), decl);
                    }
                })
        );

        soot.Main.main(args);

        Factory.singleton().getDefault().export(analyser.decls.values());
    }

    public void export() {
        for (String k: decls.keySet()) {
            TypeDeclaration decl = decls.get(k);
            System.out.println("Class Name:" + decl.getName());

            decl.getMethods().stream().forEach(m -> {
                System.out.println(" - " + m.getName());
                if(m.getDependencies() != null) {
                     m.getDependencies().stream().forEach(d -> {
                        System.out.println(d.getTypeName() + "#" + d.getMemberName());
                    });
                }
            });
        }
    }

//    public static void main(String args[]) {
//        UsageDependencies depts = new UsageDependencies();
//
//        System.out.println("-----------------------------");
//        System.out.println(" Building usage dependencies ");
//        System.out.println("-----------------------------");
//
//        soot.Main.main(args);
//
//        Chain<SootClass> classes = Scene.v().getApplicationClasses();
//
//
//        System.out.println("Number of classes: " + classes.size());
//
//        Iterator<SootClass> it = classes.iterator();
//
//
//        while(it.hasNext()) {
//            SootClass c = it.next();
//            c = Scene.v().loadClassAndSupport(c.getName());
//            depts.classTable.put(c.getName(),c);
//        }
//
//        for(String c: depts.classTable.keySet()) {
//            depts.createMethodDeps(depts.classTable.get(c), depts.classTable);
//        }
//       }
}
