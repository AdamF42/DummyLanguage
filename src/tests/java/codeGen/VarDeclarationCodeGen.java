package codeGen;


import mockit.Mock;
import mockit.MockUp;
import models.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Strings;
import static codeGen.TestUtil.getAST;
import static org.junit.jupiter.api.Assertions.*;


class VarDeclarationCodeGen {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void varDeclaration() {
        StmtBlock mainBlock = getAST("{\n int x = 3;\n  }");
        String expected =
                "li $a0 3\n" +
                "sw $a0 0($fp)\n"; // at 0 ($fp) is located the old frame pointer

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDeclarations() {
        StmtBlock mainBlock = getAST("{\n int x = 3;\n int y = 5;  }");
        String expected =
                "li $a0 3\n" +
                "sw $a0 0($fp)\n" +
                "li $a0 5\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithNumberAdd() {
        StmtBlock mainBlock = getAST("{\n int x = 3 + 1;\n }");
        String expected =
                "li $a0 3\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "$t1 <- top\n" +
                "add $a0 $a0 $t1\n" +
                "pop\n" +
                "sw $a0 0($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberAdd() {
        StmtBlock mainBlock = getAST("{\n int x = x + 1;\n }");
        String expected =
                "lw $al 0($fp)\n" +
                "lw $a0 0($al)\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "$t1 <- top\n" +
                "add $a0 $a0 $t1\n" +
                "pop\n" +
                "sw $a0 0($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithNumberSub() {
        StmtBlock mainBlock = getAST("{\n int x = 3 - 1;\n }");
        String expected =
                "li $a0 3\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "$t1 <- top\n" +
                "sub $a0 $a0 $t1\n" +
                "pop\n" +
                "sw $a0 0($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberSub() {
        StmtBlock mainBlock = getAST("{\n int x = x - 1;\n }");
        String expected =
                "lw $al 0($fp)\n" +
                "lw $a0 0($al)\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "$t1 <- top\n" +
                "sub $a0 $a0 $t1\n" +
                "pop\n" +
                "sw $a0 0($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberMult() {
        StmtBlock mainBlock = getAST("{\n int x = x * 2;\n }");
        String expected =
                "lw $al 0($fp)\n" +
                "lw $a0 0($al)\n" +
                "push $a0\n" +
                "li $a0 2\n" +
                "$t1 <- top\n" +
                "mult $a0 $a0 $t1\n" +
                "pop\n" +
                "sw $a0 0($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberDiv() {
        StmtBlock mainBlock = getAST("{\n int x = x / 2;\n }");
        String expected =
                "lw $al 0($fp)\n" +
                "lw $a0 0($al)\n" +
                "push $a0\n" +
                "li $a0 2\n" +
                "$t1 <- top\n" +
                "div $a0 $a0 $t1\n" +
                "pop\n" +
                "sw $a0 0($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecsWithComplexExp() {
        StmtBlock mainBlock = getAST("{ int y = 6; int x = (y+1)*((x-1) / 2); }");
        //TODO: not sure if it is correct...check it on paper...
        String expected =
                "li $a0 6\n" +
                "sw $a0 0($fp)\n" +
                "lw $al 0($fp)\n" +
                "lw $a0 0($al)\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "$t1 <- top\n" +
                "add $a0 $a0 $t1\n" +
                "pop\n" +
                "push $a0\n" +
                "lw $al 0($fp)\n" +
                "lw $a0 4($al)\n" +
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
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithSimpleBooleanAssignment() {
        StmtBlock mainBlock = getAST("{ bool x = true; }");
        String expected =
                "li $a0 1\n" +
                "sw $a0 0($fp)\n";

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

        StmtBlock mainBlock = getAST("{ bool x = true && false; }");
        String expected =
                "li $a0 1\n" +
                "beq $a0 0 end\n" +
                "li $a0 0\n" +
                "end:\n" +
                "sw $a0 0($fp)\n";
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

        StmtBlock mainBlock = getAST("{ bool x = true || false; }");
        String expected =
                "li $a0 1\n" +
                "beq $a0 1 end\n" +
                "li $a0 0\n" +
                "end:\n" +
                "sw $a0 0($fp)\n";

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

        StmtBlock mainBlock = getAST("{ bool x = (false || false) && (true && false) ; }");
        //TODO: not sure if it is correct...check it on paper...
        String expected =
                "li $a0 0\n" +
                "beq $a0 1 end\n" +
                "li $a0 0\n" +
                "end:\n" +
                "beq $a0 0 end\n" +
                "li $a0 1\n" +
                "beq $a0 0 end\n" +
                "li $a0 0\n" +
                "end:\n" +
                "end:\n" +
                "sw $a0 0($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

}