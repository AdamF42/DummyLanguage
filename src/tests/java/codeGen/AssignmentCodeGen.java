package codeGen;

import models.compiler.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;

class AssignmentCodeGen {


    @Test
    void assignmentInTheSameScope() {
        StmtBlock mainBlock = GetAST("{ int x = 1; x = 3; }");
        String expected =
                OpenScopeWithVars(1,true) +
                    "li $a0 1\n" +
                    "sw $a0 4($fp)\n" +
                    "li $a0 3\n" +
                    "sw $a0 4($fp)\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }


    @Test
    void assignmentInNestedScopeWithValueInt() {
        StmtBlock mainBlock = GetAST("{\n int x = 1; {{x = 3;\n }}}");
        String expected =
                OpenScopeWithVars(1,true) +
                    "li $a0 1\n" +
                    "sw $a0 4($fp)\n" +
                    OpenScopeWithVars(0,false) +
                        OpenScopeWithVars(0,false) +
                            "li $a0 3\n" +
                            "lw $al 0($fp)\n" +
                            "lw $al 0($al)\n" +
                            "sw $a0 4($al)\n" +
                        CloseScopeWithVars(0) +
                    CloseScopeWithVars(0) +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void assignmentInNestedScopeWithValueBool() {
        StmtBlock mainBlock = GetAST("{\n bool x = false; {{x = true;\n }}}");
        String expected =
                OpenScopeWithVars(1,true) +
                    "li $a0 0\n" +
                    "sw $a0 4($fp)\n" +
                    OpenScopeWithVars(0,false) +
                        OpenScopeWithVars(0,false) +
                            "li $a0 1\n" +
                            "lw $al 0($fp)\n" +
                            "lw $al 0($al)\n" +
                            "sw $a0 4($al)\n" +
                        CloseScopeWithVars(0) +
                    CloseScopeWithVars(0) +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void assignmentInNestedScopeWithValueId() {
        StmtBlock mainBlock = GetAST("{\n int y = 1; int x = 0; {{x = y;\n }}}");
        String expected =
                OpenScopeWithVars(2,true) +
                    "li $a0 1\n" +
                    "sw $a0 4($fp)\n" +
                    "li $a0 0\n" +
                    "sw $a0 8($fp)\n" +
                    OpenScopeWithVars(0,false) +
                        OpenScopeWithVars(0,false) +
                            "lw $al 0($fp)\n" +
                            "lw $al 0($al)\n" +
                            "lw $a0 4($al)\n" + // cgen(y)

                            "lw $al 0($fp)\n" +
                            "lw $al 0($al)\n" +
                            "sw $a0 8($al)\n" + // cgen(x=y)
                        CloseScopeWithVars(0) +
                    CloseScopeWithVars(0) +
                CloseScopeWithVars(2);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }
}
