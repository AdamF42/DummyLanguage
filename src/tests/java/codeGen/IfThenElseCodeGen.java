package codeGen;

import mockit.Mock;
import mockit.MockUp;
import models.compiler.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Strings;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;

class IfThenElseCodeGen {

    private static final String X_DECLARATION = "li $a0 1\nsw $a0 4($fp)\n";
    private static final String CGEN_X = "lw $a0 4($fp)\n";
    private static final String CGEN_1 =  "li $a0 1\n";

    private static int label_count;

    @BeforeEach
    void setUp() {
        label_count = 0;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void ifThenElseWithEqCondition() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "ifThenElse_end");
        labels.put(2, "elseBranch");
        labels.put(3, "equal");
        labels.put(4, "condition_end");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };

        StmtBlock mainBlock = GetAST("{ int x = 1; if( x==1 ) then{ } else{ } }");
        String expected =
                OpenScopeWithVars(1, true) +
                    X_DECLARATION +
                    CGEN_X +
                    "push $a0\n" +
                    CGEN_1 +
                    "$t1 <- top\n" +
                    "pop\n" +
                    "beq $a0 $t1 equal\n" +
                    "li $a0 0\n" +
                    "b condition_end\n" +
                    "equal:\n" +
                    "li $a0 1\n" +
                    "condition_end:\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 elseBranch\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "b ifThenElse_end\n" +
                    "elseBranch:\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "ifThenElse_end:\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void ifThenElseWithNotEqCondition() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "ifThenElse_end");
        labels.put(2, "elseBranch");
        labels.put(3, "equal");
        labels.put(4, "condition_end");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };

        StmtBlock mainBlock = GetAST("{ int x = 1; if( x!=1 ) then{ } else{ } }");
        String expected =
                OpenScopeWithVars(1, true) +
                    X_DECLARATION +
                    CGEN_X +
                    "push $a0\n" +
                    CGEN_1 +
                    "$t1 <- top\n" +
                    "pop\n" +
                    "beq $a0 $t1 equal\n" +
                    "li $a0 1\n" +
                    "b condition_end\n" +
                    "equal:\n" +
                    "li $a0 0\n" +
                    "condition_end:\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 elseBranch\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "b ifThenElse_end\n" +
                    "elseBranch:\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "ifThenElse_end:\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }


    @Test
    void ifThenElseWithGrCondition() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "ifThenElse_end");
        labels.put(2, "elseBranch");
        labels.put(3, "greater");
        labels.put(4, "condition_end");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };

        StmtBlock mainBlock = GetAST("{ int x = 1; if(x>1) then{ } else{ }}");
        String expected =
                OpenScopeWithVars(1, true) +
                    X_DECLARATION +
                    CGEN_X +
                    "push $a0\n" +
                    CGEN_1 +
                    "$t1 <- top\n" +
                    "pop\n" +
                    "bgr $a0 $t1 greater\n" +
                    "li $a0 0\n" +
                    "b condition_end\n" +
                    "greater:\n" +
                    "li $a0 1\n" +
                    "condition_end:\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 elseBranch\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "b ifThenElse_end\n" +
                    "elseBranch:\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "ifThenElse_end:\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void ifThenElseWithGreCondition() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "ifThenElse_end");
        labels.put(2, "elseBranch");
        labels.put(3, "greater_or_equal");
        labels.put(4, "condition_end");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };

        StmtBlock mainBlock = GetAST("{ int x = 1; if(x>=1) then{ } else{ }}");
        String expected =
                OpenScopeWithVars(1, true) +
                    X_DECLARATION +
                    CGEN_X +
                    "push $a0\n" +
                    CGEN_1 +
                    "$t1 <- top\n" +
                    "pop\n" +
                    "bgre $a0 $t1 greater_or_equal\n" +
                    "li $a0 0\n" +
                    "b condition_end\n" +
                    "greater_or_equal:\n" +
                    "li $a0 1\n" +
                    "condition_end:\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 elseBranch\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "b ifThenElse_end\n" +
                    "elseBranch:\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "ifThenElse_end:\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void ifThenElseWithLoCondition() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "ifThenElse_end");
        labels.put(2, "elseBranch");
        labels.put(3, "lower");
        labels.put(4, "condition_end");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };

        StmtBlock mainBlock = GetAST("{ int x = 1; if(x<1) then{ } else{ }}");
        String expected =
                OpenScopeWithVars(1, true) +
                    X_DECLARATION +
                    CGEN_X +
                    "push $a0\n" +
                    CGEN_1 +
                    "$t1 <- top\n" +
                    "pop\n" +
                    "blr $a0 $t1 lower\n" +
                    "li $a0 0\n" +
                    "b condition_end\n" +
                    "lower:\n" +
                    "li $a0 1\n" +
                    "condition_end:\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 elseBranch\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "b ifThenElse_end\n" +
                    "elseBranch:\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "ifThenElse_end:\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void ifThenElseWithLoeCondition() {

        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "ifThenElse_end");
        labels.put(2, "elseBranch");
        labels.put(3, "lower_or_equal");
        labels.put(4, "condition_end");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };

        StmtBlock mainBlock = GetAST("{ int x = 1; if(x<=1) then{ } else{ }}");
        String expected =
                OpenScopeWithVars(1, true) +
                    X_DECLARATION +
                    CGEN_X +
                    "push $a0\n" +
                    CGEN_1 +
                    "$t1 <- top\n" +
                    "pop\n" +
                    "blre $a0 $t1 lower_or_equal\n" +
                    "li $a0 0\n" +
                    "b condition_end\n" +
                    "lower_or_equal:\n" +
                    "li $a0 1\n" +
                    "condition_end:\n" +
                    "li $t1 0\n" +
                    "beq $a0 $t1 elseBranch\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "b ifThenElse_end\n" +
                    "elseBranch:\n" +
                    OpenScopeWithVars(0, false) +
                    CloseScopeWithVars(0) +
                    "ifThenElse_end:\n" +
                CloseScopeWithVars(1);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }
}
