package codeGen;


import mockit.Mock;
import mockit.MockUp;
import compilermodels.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Strings;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestUtil.*;


class VarDeclarationCodeGen {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void varDeclaration() {
        StmtBlock mainBlock = GetAST("{ int x = 3; }");
        String expected =
                OpenScopeWithVars(1) +
                    "li $a0 3\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDeclarations() {
        StmtBlock mainBlock = GetAST("{ int x = 3; int y = 5;  }");
        String expected =
                OpenScopeWithVars(2) +
                    "li $a0 3\n" +
                    "sw $a0 4($fp)\n" +
                    "li $a0 5\n" +
                    "sw $a0 8($fp)\n" +
                CloseScopeWithVars(2);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithNumberAdd() {
        StmtBlock mainBlock = GetAST("{ int x = 3 + 1; }");
        String expected =
                OpenScopeWithVars(1) +
                    "li $a0 3\n" +
                    "push $a0\n" +
                    "li $a0 1\n" +
                    "$t1 <- top\n" +
                    "add $a0 $a0 $t1\n" +
                    "pop\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberAdd() {
        StmtBlock mainBlock = GetAST("{ int x = 0; x = x + 1; }"); //TODO: se in x ci metto un valore random, che succede??? Impedisci la dichiarazione ricorsiva. "Variable x might not be initialized"
        String expected =
                OpenScopeWithVars(1) +
                    "li $a0 0\n" +
                    "sw $a0 4($fp)\n" +
                    "lw $a0 4($fp)\n" +
                    "push $a0\n" +
                    "li $a0 1\n" +
                    "$t1 <- top\n" +
                    "add $a0 $a0 $t1\n" +
                    "pop\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithNumberSub() {
        StmtBlock mainBlock = GetAST("{ int x = 3 - 1; }");
        String expected =
                OpenScopeWithVars(1) +
                "li $a0 3\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "$t1 <- top\n" +
                "sub $a0 $a0 $t1\n" +
                "pop\n" +
                "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberSub() {
        StmtBlock mainBlock = GetAST("{int x = 0; x = x - 1; }"); //TODO: stesso problema di prima
        String expected =
                OpenScopeWithVars(1) +
                    "li $a0 0\n" +
                    "sw $a0 4($fp)\n" +
                    "lw $a0 4($fp)\n" +
                    "push $a0\n" +
                    "li $a0 1\n" +
                    "$t1 <- top\n" +
                    "sub $a0 $a0 $t1\n" +
                    "pop\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberMult() {
        StmtBlock mainBlock = GetAST("{int x = 0; x = x * 2; }"); //TODO: stesso problema di prima
        String expected =
                OpenScopeWithVars(1) +
                    "li $a0 0\n" +
                    "sw $a0 4($fp)\n" +
                    "lw $a0 4($fp)\n" +
                    "push $a0\n" +
                    "li $a0 2\n" +
                    "$t1 <- top\n" +
                    "mult $a0 $a0 $t1\n" +
                    "pop\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberDiv() {
        StmtBlock mainBlock = GetAST("{ int x = 0; x = x / 2; }");
        String expected =
                OpenScopeWithVars(1) +
                    "li $a0 0\n" +
                    "sw $a0 4($fp)\n" +
                    "lw $a0 4($fp)\n" +
                    "push $a0\n" +
                    "li $a0 2\n" +
                    "$t1 <- top\n" +
                    "div $a0 $a0 $t1\n" +
                    "pop\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecsWithComplexExp() {
        StmtBlock mainBlock = GetAST("{ int y = 6; int x = 0; x = (y+1)*((x-1) / 2); }");
        String expected =
                OpenScopeWithVars(2) +
                    "li $a0 6\n" +
                    "sw $a0 4($fp)\n" +
                    "li $a0 0\n" +
                    "sw $a0 8($fp)\n" +
                    "lw $a0 4($fp)\n" +
                    "push $a0\n" +
                    "li $a0 1\n" +
                    "$t1 <- top\n" +
                    "add $a0 $a0 $t1\n" +
                    "pop\n" +
                    "push $a0\n" +
                    "lw $a0 8($fp)\n" +
                    "push $a0\n" +
                    "li $a0 1\n" +
                    "$t1 <- top\n" +
                    "sub $a0 $a0 $t1\n" +
                    "pop\n" +
                    "push $a0\n" +
                    "li $a0 2\n" +
                    "$t1 <- top\n" +
                    "div $a0 $a0 $t1\n" +
                    "pop\n" +
                    "$t1 <- top\n" +
                    "mult $a0 $a0 $t1\n" +
                    "pop\n" +
                    "sw $a0 8($fp)\n" +
                CloseScopeWithVars(2);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithSimpleBooleanAssignment() {
        StmtBlock mainBlock = GetAST("{ bool x = true; }");
        String expected =
                OpenScopeWithVars(1) +
                    "li $a0 1\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }


    @Test
    void varDecWithBooleanExpressionAnd() {

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                return "end";
            }
        };

        StmtBlock mainBlock = GetAST("{ bool x = true && false; }");
        String expected =
                OpenScopeWithVars(1) +
                    "li $a0 1\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 end\n" +
                    "li $a0 0\n" +
                    "end:\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);
        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithBooleanExpressionOr() {

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                return "end";
            }
        };

        StmtBlock mainBlock = GetAST("{ bool x = true || false; }");
        String expected =
                OpenScopeWithVars(1) +
                    "li $a0 1\n" +
                    "li $t1 1\n" +
                    "beq $a0 $t1 end\n" +
                    "li $a0 0\n" +
                    "end:\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithBooleanExpressionComplex() {

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                return "end";
            }
        };

        StmtBlock mainBlock = GetAST("{ bool x = (false || false) && (true && false) ; }");
        String expected =
                OpenScopeWithVars(1) +
                "li $a0 0\n" +
                "li $t1 1\n" +
                "beq $a0 $t1 end\n" +
                "li $a0 0\n" +
                "end:\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 end\n" +
                "li $a0 1\n" +
                "li $t1 0\n" +
                "beq $a0 $t1 end\n" +
                "li $a0 0\n" +
                "end:\n" +
                "end:\n" +
                "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

}