package br.unb.cic.sa.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import polyglot.ast.MethodDecl;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class or interface declaration.
 * Note that, here, we are using Lombok to generate
 * all getters and setters + builders and all that kind
 * of stuff.
 */
@Data
@Builder
public class TypeDeclaration {
    private String name;

    private List<MethodDeclaration> methods;

    /**
     * Add a method declaration to the type.
     * @param m new method to be introduced in the type.
     */
    public void addMethod(MethodDeclaration m) {
        if(methods == null) {
            methods = new ArrayList<>();
        }
        methods.add(m);
    }
}
