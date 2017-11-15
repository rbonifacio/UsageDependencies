package br.unb.cic.sa.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

/**
 * Unit test class for {@link MethodDeclarationTest}
 */
public class MethodDeclarationTest {
    @Test
    public void testSignature() {
        MethodDeclaration md = MethodDeclaration.builder()
                .returnType("void")
                .name("foo")
                .pmt("int")
                .pmt("int")
                .build();

        assertEquals("void foo(int, int)", md.signature());
    }
}
