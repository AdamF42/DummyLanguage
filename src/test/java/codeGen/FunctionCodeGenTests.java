package codeGen;

import mockit.Mock;
import mockit.MockUp;
import models.compiler.statements.StmtBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Strings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;


class FunctionCodeGenTests {

    @BeforeEach
    void setUp() {
        new MockUp<Strings>() {
            @Mock
            public String getFreshLabel() {
                return "f_entry";
            }
        };
    }

    private static final String SET_ACCESS_LINK =
            "lw $al 0($fp)\n" +
            "lw $al 0($al)\n" +
            "push $al\n";

    private static final String F_DECLARATION =
                    "b f_entry\n" +
                    "f_entry:\n" +
                    "lw $al 0($fp)\n" +
                    "lw $al 0($al)\n" +
                    "push $al\n" +
                    "move $fp $sp\n" +
                    "push $ra\n" +
                    "$ra <- top\n" +
                    "pop\n" +
                    "pop\n" +
                    "move $t1 $sp\n" +
                    "addi $sp $sp 4\n" +
                    "$fp <- top\n" +
                    "pop\n" +
                    "jr $ra\n" +
                    "f_entry:\n";

    @Test
    void simpleFunDeclaration() {

        StmtBlock mainBlock = GetAST("" +
                    "{" +
                    "    f(int x){" +
                    "    }" +
                    "}");
        String expected =
                OpenScopeWithVars(0,true) +
                "b f_entry\n" +
                "f_entry:\n" +
                SET_ACCESS_LINK +
                "move $fp $sp\n" +
                "push $ra\n" +
                "$ra <- top\n" +
                "pop\n" +
                "pop\n" +
                "move $t1 $sp\n" +
                "addi $sp $sp 4\n" +
                "$fp <- top\n" +
                "pop\n" +
                "jr $ra\n" +
                "f_entry:\n" +
                CloseScopeWithVars(0);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void simpleFunDeclarationAndCall() {

        StmtBlock mainBlock = GetAST(
                "{" +
                    "    f(int x){" +
                    "    }" +
                    "    f(42);" +
                    "}");
        String expected =
                OpenScopeWithVars(0,true) +
                        F_DECLARATION +
                        "push $fp\n" +
                        "li $a0 42\n" +
                        "push $a0\n" +
                        "jal f_entry\n" +
                CloseScopeWithVars(0);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void funWithOneReferenceParameter_ShouldReturn_ExpectedCode() {

        StmtBlock mainBlock = GetAST(
                "{\n" +
                        "int b = 41;\n" +
                        "f (var int c){\n" +
                        "   c = c + 1;" +
                            "print c;\n" +
                        "}\n" +
                    "    {\n" +
                    "        f(b);\n" +
                    "        print b;" +
                    "    }\n" +
                    "}");
        String expected = "push $fp\n" +
                "addi $sp $sp -4\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "sw $fp 0($fp)\n" +
                "li $a0 41\n" +
                "sw $a0 4($fp)\n" +
                "b f_entry\n" +
                "f_entry:\n" +
                "lw $al 0($fp)\n" +
                "lw $al 0($al)\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "push $ra\n" +
                "lw $a0 4($fp)\n" +
                "push $a0\n" +
                "li $a0 1\n" +
                "$t1 <- top\n" +
                "add $a0 $a0 $t1\n" +
                "pop\n" +
                "sw $a0 4($fp)\n" +
                "lw $a0 4($fp)\n" +
                "print\n" +
                "$ra <- top\n" +
                "pop\n" +
                "pop\n" +
                "move $t1 $sp\n" +
                "addi $sp $sp 4\n" +
                "$fp <- top\n" +
                "pop\n" +
                "jr $ra\n" +
                "f_entry:\n" +
                "push $fp\n" +
                "push $fp\n" +
                "move $fp $sp\n" +
                "push $fp\n" +
                "lw $al 0($fp)\n" +
                "lw $a0 4($al)\n" +
                "push $a0\n" +
                "jal f_entry\n" +
                "lw $a0 0($t1)\n" +
                "lw $al 0($fp)\n" +
                "sw $a0 4($al)\n" +
                "lw $al 0($fp)\n" +
                "lw $a0 4($al)\n" +
                "print\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n" +
                "pop\n" +
                "addi $sp $sp 4\n" +
                "$fp <- top\n" +
                "pop\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }
}
