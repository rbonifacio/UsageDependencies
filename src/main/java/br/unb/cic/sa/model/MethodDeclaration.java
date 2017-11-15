package br.unb.cic.sa.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a method declaration.
 */
@Data
@Builder
public class MethodDeclaration {
    private String returnType;
    private String name;

    @Singular
    private List<String> pmts;

    //@Singular
    private List<Dependency> dependencies;

    /**
     * Returns the method signature.
     * @return
     */
    public String signature() {
        return returnType + " " +
                name + "(" +
                pmts.stream().collect(Collectors.joining(", ")) +
                ")";
    }

    /**
     * Add a (fine-grained) dependency to the method declaration
     * @param d a new dependency
     */
    public void addDependency(Dependency d) {
        if(dependencies == null) {
            dependencies = new ArrayList<>();
        }
        dependencies.add(d);
    }
}
