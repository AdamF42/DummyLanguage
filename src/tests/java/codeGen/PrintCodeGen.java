package codeGen;

import models.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;

public class PrintCodeGen {

    private static final String X_DECLARATION =
            "li $a0 1\n" +
            "sw $a0 0($fp)\n";


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void print() {
        StmtBlock mainBlock = getAST("{\n int x = 1; print x;\n  }");
        String expected =
                OPEN_SCOPE +
                    X_DECLARATION +
                    "lw $al 0($fp)\n" +
                    "lw $a0 0($al)\n" +
                    "print\n" +
                CLOSE_SCOPE;

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }
}
