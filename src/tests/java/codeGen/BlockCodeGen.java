package codeGen;

import models.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;

public class BlockCodeGen {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    private static final String X_CGEN =
                "li $a0 1\n" +
                "sw $a0 0($fp)\n";

    private static final String Y_CGEN =
                "li $a0 1\n" +
                "sw $a0 4($fp)\n";

    @Test
    void simpleBlock() {
        StmtBlock mainBlock = GetAST("{ }");
        String expected =
                "push $fp\n" +
                "li $t1 0\n" +
                "move $fp $sp\n" +
                "$fp <- top\n" +
                "pop\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void simpleBlockWithVarDec() {
        StmtBlock mainBlock = GetAST("{ int x=1;}");
        String expected =
                OpenScopeWithVars(1) +
                        X_CGEN +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void simpleBlockWithVarDecs() {
        StmtBlock mainBlock = GetAST("{ int x=1; int y=1;}");
        String expected =
                OpenScopeWithVars(2) +
                    X_CGEN +
                    Y_CGEN +
                CloseScopeWithVars(2);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void nestedBlockWithVarDec() {
        StmtBlock mainBlock = GetAST("{ {int x=1;} }");
        String expected =
                OpenScopeWithVars(0) +
                    OpenScopeWithVars(1) +
                    X_CGEN +
                    CloseScopeWithVars(1) +
                CloseScopeWithVars(0);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void nestedBlockWithVarDecs() {
        StmtBlock mainBlock = GetAST("{ {int x=1; int y=1;} }");
        String expected =
                OpenScopeWithVars(0) +
                    OpenScopeWithVars(2) +
                        X_CGEN +
                        Y_CGEN +
                    CloseScopeWithVars(2) +
                CloseScopeWithVars(0);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void nestedBlockWithVarDecInOuterBlock() {
        StmtBlock mainBlock = GetAST("{ int x=1; {int y=1;}}");
        String expected =
                OpenScopeWithVars(1) +
                    X_CGEN +
                    OpenScopeWithVars(1) +
                        Y_CGEN +
                    CloseScopeWithVars(1) +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

}
