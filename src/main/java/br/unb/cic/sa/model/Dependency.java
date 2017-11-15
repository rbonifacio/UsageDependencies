package br.unb.cic.sa.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a fine-grained dependency.
 *
 * Note that we do not need to perform any kind of
 * type checking, so we just kepp strings to represent
 * a dependency.
 */
@Data
@Builder
public class Dependency {
    private String typeName;
    private String memberName;
}
