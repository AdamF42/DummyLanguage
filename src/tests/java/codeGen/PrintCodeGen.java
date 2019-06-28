package codeGen;

import models.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;

public class PrintCodeGen {

    private static final String CGEN_X =
            "li $a0 1\n" +
            "sw $a0 0($fp)\n";

    private static final String CGEN_EXP =
            "li $a0 3\n" +
            "push $a0\n" +
            "li $a0 1\n" +
            "$t1 <- top\n" +
            "add $a0 $a0 $t1\n" +
            "pop\n";



    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void printVariable() {
        StmtBlock mainBlock = GetAST("{\n int x = 1; print x;\n  }");
        String expected =
                OpenScopeWithVars(1) +
                    CGEN_X +
                    "lw $al 0($fp)\n" +
                    "lw $a0 0($al)\n" +
                    "print\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void printExpression() {
        StmtBlock mainBlock = GetAST("{\n print 3+1;\n  }");
        String expected =
                OpenScopeWithVars(0) +
                    CGEN_EXP +
                    "print\n" +
                CloseScopeWithVars(0);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }
}
