package codeGen;

import models.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static codeGen.TestUtil.getAST;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssignmentCodeGen {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void assignmentInTheSameScope() {
        StmtBlock mainBlock = getAST("{\n int x = 1; x = 3;\n  }");
        String expected =
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "li $a0 1\n" +
                "sw $a0 0($fp)\n" +
                "li $a0 3\n" +
                "lw $al 0($fp)\n" +
                "sw $a0 0($al)\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }


    @Test
    void assignmentInNestedScopeWithValueInt() {
        StmtBlock mainBlock = getAST("{\n int x = 1; {{x = 3;\n }}}");
        String expected =
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "li $a0 1\n" +
                "sw $a0 0($fp)\n" +
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "li $a0 3\n" +
                "lw $al 0($fp)\n" +
                "lw $al 0($al)\n" +
                "lw $al 0($al)\n" +
                "sw $a0 0($al)\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void assignmentInNestedScopeWithValueBool() {
        StmtBlock mainBlock = getAST("{\n bool x = false; {{x = true;\n }}}");
        String expected =
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "li $a0 0\n" +
                "sw $a0 0($fp)\n" +
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "li $a0 1\n" +
                "lw $al 0($fp)\n" +
                "lw $al 0($al)\n" +
                "lw $al 0($al)\n" +
                "sw $a0 0($al)\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void assignmentInNestedScopeWithValueId() {
        StmtBlock mainBlock = getAST("{\n int y = 1; int x = 0; {{x = y;\n }}}");
        String expected =
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +

                "li $a0 1\n" +
                "sw $a0 0($fp)\n" +
                "li $a0 0\n" +
                "sw $a0 4($fp)\n" +
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "lw $al 0($fp)\n" +
                "lw $al 0($al)\n" +
                "lw $al 0($al)\n" +
                "lw $a0 0($al)\n" + // cgen(y)

                "lw $al 0($fp)\n" +
                "lw $al 0($al)\n" +
                "lw $al 0($al)\n" +
                "sw $a0 4($al)\n" + // cgen(x=y)
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }
}
