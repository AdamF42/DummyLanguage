package codeGen;

import mockit.Mock;
import mockit.MockUp;
import compilermodels.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.Strings;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;
import static utils.TestUtil.CloseScopeWithVars;

@Disabled("Not ready yet. Explicitly Disabled")
class ComplexSamples {
    private static final String X_DECLARATION = "li $a0 1\nsw $a0 4($fp)\n";
    private static final String CGEN_O = "li $a0 0\n";
    private static final String CGEN_Y =
            "lw $al 0($fp)\n" +
            "lw $a0 4($al)\n";

    private static final String CGEN_N =
            "lw $al 0($fp)\n" +
            "lw $a0 8($al)\n";

    private static final String CGEN_CONDITION =
            CGEN_Y +
            "push $a0\n" +
            CGEN_O +
            "$t1 <- top\n" +
            "pop\n" +
            "beq $a0 $t1 equal\n" +
            "li $a0 0\n" +
            "b condition_end\n" +
            "equal:\n" +
            "li $a0 1\n" +
            "condition_end:\n";

    private static final String CGEN_CONDITION2 =
            CGEN_N +
            "push $a0\n" +
            CGEN_O +
            "$t1 <- top\n" +
            "pop\n" +
            "beq $a0 $t1 equal\n" +
            "li $a0 0\n" +
            "b condition_end\n" +
            "equal:\n" +
            "li $a0 1\n" +
            "condition_end:\n";

    private static final String CGEN_CONDITION3 =
            "lw $al 0($fp)\n" +
            "lw $a0 0($al)\n" +
            "push $a0\n" +
            "lw $al 0($fp)\n" +
            "lw $a0 4($al)\n" +
            "$t1 <- top\n" +
            "pop\n" +
            "bgr $a0 $t1 greater\n" +
            "li $a0 0\n" +
            "b condition_end\n" +
            "greater:\n" +
            "li $a0 1\n" +
            "condition_end:\n";

    private static int label_count;

    @BeforeEach
    void setUp() {
        label_count = 0;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void CodeGeneration_ShouldMatchExpected_WithFunDecAndCall() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "f_entry");
        labels.put(2, "if_end");
        labels.put(3, "elseBranch");
        labels.put(4, "equal");
        labels.put(5, "condition_end");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };

        StmtBlock mainBlock = GetAST(
                "{" +
                "    int x = 1;" +
                "    f(int y){" +
                "        if (y == 0) then {" +
                "            print(x);" +
                "        } else {" +
                "            f(y-1) ;" +
                "        }" +
                "    }" +
                "    f(54) ;" +
                "}");

        String expected =
                OpenScopeWithVars(1) +
                    X_DECLARATION +
                    "f_entry:\n" +
                    "move $fp $sp\n" +
                    "push $ra\n" +
                    //cgen(e)
                    CGEN_CONDITION+
                    "li $t1 0\n" +
                    "beq $a0 $t1 elseBranch\n" +
                    OpenScopeWithVars(0) +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 0($al)\n" +
                        "print\n" +
                    CloseScopeWithVars(0) +
                    "b if_end\n" +
                    "elseBranch:\n" +
                    OpenScopeWithVars(0) +
                        "push $fp\n" +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 4($al)\n" +
                        "push $a0\n" +
                        "li $a0 1\n" +
                        "$t1 <- top\n" +
                        "sub $a0 $a0 $t1\n" +
                        "pop\n" +
                        "push $a0\n" +
                        "jal f_entry\n" +
                    CloseScopeWithVars(0) +
                    "if_end:\n" +

                    "$ra <- top\n" +
                    "addi $sp $sp 4\n" +
                    "$fp <- top\n" +
                    "pop\n" +
                    "jr $ra\n" +
                    "push $fp\n" +
                    "li $a0 54\n" +
                    "push $a0\n" +
                    "jal f_entry\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void codeGeneration_ShouldMatchExpected_WithInfinitRecursion() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "f_entry");
        labels.put(2, "if_end");
        labels.put(3, "elseBranch");
        labels.put(4, "equal");
        labels.put(5, "condition_end");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };

        StmtBlock mainBlock = GetAST(
                "{" +
                    "    int u = 1 ;\n" +
                    "    f(var int x, int n){\n" +
                    "        if (n == 0) then { print(x) ; }\n" +
                    "        else { int y = x*n ; f(y,n-1) ; }\n" +
                    "        delete x ;\n" +
                    "    }\n" +
                    "    f(u,6) ;\n" +
                    "}");

        String expected =
                OpenScopeWithVars(1) +
                    X_DECLARATION +
                    "f_entry:\n" +
                    "move $fp $sp\n" +
                    "push $ra\n" +
                    CGEN_CONDITION2+
                    "li $t1 0\n" +
                    "beq $a0 $t1 elseBranch\n" +
                    OpenScopeWithVars(0) +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 4($al)\n" +
                        "print\n" +
                    CloseScopeWithVars(0) +
                    "b if_end\n" +
                    "elseBranch:\n" +
                    OpenScopeWithVars(1) +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 4($al)\n" +
                        "push $a0\n" +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 8($al)\n" +
                        "$t1 <- top\n" +
                        "mult $a0 $a0 $t1\n" +
                        "pop\n" +
                        "sw $a0 0($fp)\n" +
                        "push $fp\n" +
                        "lw $al 0($fp)\n" +
                        "lw $a0 0($al)\n" +
                        "push $a0\n" +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 8($al)\n" +
                        "push $a0\n" +
                        "li $a0 1\n" +
                        "$t1 <- top\n" +
                        "sub $a0 $a0 $t1\n" +
                        "pop\n" +
                        "push $a0\n" +
                        "jal f_entry\n" +
                    CloseScopeWithVars(1) +
                    "if_end:\n" +

                    "$ra <- top\n" +
                    "addi $sp $sp 8\n" +
                    "$fp <- top\n" +
                    "pop\n" +
                    "jr $ra\n" +
                    "push $fp\n" +
                    "lw $al 0($fp)\n" +
                    "lw $a0 0($al)\n" +
                    "push $a0\n" +
                    "li $a0 6\n" +
                    "push $a0\n" +
                    "jal f_entry\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void codeGeneration_ShouldMatchExpected_WithInfinitRecursion2() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "f_entry");
        labels.put(2, "if_end");
        labels.put(3, "elseBranch");
        labels.put(4, "greater");
        labels.put(5, "condition_end");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };

        StmtBlock mainBlock = GetAST(
                "{" +
                    "    f(int m, int n){" +
                    "        if (m>n) then { print(m+n) ;}" +
                    "        else { int x = 1 ; f(m+1,n+1) ; }" +
                    "    }" +
                    "    f(5,4);" +
                    "}");

        String expected =
                OpenScopeWithVars(0) +
                        "f_entry:\n" +
                        "move $fp $sp\n" +
                        "push $ra\n" +
                        CGEN_CONDITION3+
                        "li $t1 0\n" +
                        "beq $a0 $t1 elseBranch\n" +
                        OpenScopeWithVars(0) +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 0($al)\n" +
                        "push $a0\n" +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 4($al)\n" +
                        "$t1 <- top\n" +
                        "add $a0 $a0 $t1\n" +
                        "pop\n" +
                        "print\n" +
                        CloseScopeWithVars(0) +
                        "b if_end\n" +
                        "elseBranch:\n" +
                        OpenScopeWithVars(1) +
                        "li $a0 1\n" +
                        "sw $a0 0($fp)\n" +
                        "push $fp\n" +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 0($al)\n" +
                        "push $a0\n" +
                        "li $a0 1\n" +
                        "$t1 <- top\n" +
                        "add $a0 $a0 $t1\n" +
                        "pop\n" +
                        "push $a0\n" +
                        "lw $al 0($fp)\n" +
                        "lw $al 0($al)\n" +
                        "lw $a0 4($al)\n" +
                        "push $a0\n" +
                        "li $a0 1\n" +
                        "$t1 <- top\n" +
                        "add $a0 $a0 $t1\n" +
                        "pop\n" +
                        "push $a0\n" +
                        "jal f_entry\n" +
                        CloseScopeWithVars(1) +
                        "if_end:\n" +

                        "$ra <- top\n" +
                        "addi $sp $sp 8\n" +
                        "$fp <- top\n" +
                        "pop\n" +
                        "jr $ra\n" +
                        "push $fp\n" +
                        "li $a0 5\n" +
                        "push $a0\n" +
                        "li $a0 4\n" +
                        "push $a0\n" +
                        "jal f_entry\n" +
                        CloseScopeWithVars(0);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

}
