package codeGen;

import models.compiler.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;

class BlockCodeGen {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    private static final String X_CGEN =
                "li $a0 1\n" +
                "sw $a0 4($fp)\n";

    private static final String Y_CGEN =
                "li $a0 1\n" +
                "sw $a0 4($fp)\n";

    private static final String Y_CGEN2 =
            "li $a0 1\n" +
            "sw $a0 8($fp)\n";

    @Test
    void simpleBlock() {
        StmtBlock mainBlock = GetAST("{ }");
        String expected =
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "sw $fp 0($fp)\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void simpleBlockWithVarDec() {
        StmtBlock mainBlock = GetAST("{ int x = 1;}");
        String expected =
                OpenScopeWithVars(1,true) +
                        X_CGEN +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void simpleBlockWithVarDecs() {
        StmtBlock mainBlock = GetAST("{ int x=1; int y=1;}");
        String expected =
                OpenScopeWithVars(2,true) +
                    X_CGEN +
                    "li $a0 1\n" +
                    "sw $a0 8($fp)\n" +
                CloseScopeWithVars(2);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void nestedBlockWithVarDec() {
        StmtBlock mainBlock = GetAST("{ {int x=1;} }");
        String expected =
                OpenScopeWithVars(0,true) +
                    OpenScopeWithVars(1, false) +
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
                OpenScopeWithVars(0,true) +
                    OpenScopeWithVars(2,false) +
                        X_CGEN +
                        Y_CGEN2 +
                    CloseScopeWithVars(2) +
                CloseScopeWithVars(0);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void nestedBlockWithVarDecInOuterBlock() {
        StmtBlock mainBlock = GetAST("{ int x=1; {int y=1;}}");
        String expected =
                OpenScopeWithVars(1,true) +
                    X_CGEN +
                    OpenScopeWithVars(1,false) +
                        Y_CGEN +
                    CloseScopeWithVars(1) +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

}
