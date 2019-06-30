package codeGen;

import mockit.Mock;
import mockit.MockUp;
import compilermodels.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.Strings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;


@Disabled("Not ready yet. Explicitly Disabled")
class FunctionCodeGen {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    private static final String F_DECLARATION =
                    "f_entry:\n" +
                    "move $fp $sp\n" +
                    "push $ra\n" +
                    "$ra <- top\n" +
                    "addi $sp $sp 4\n" +
                    "$fp <- top\n" +
                    "pop\n" +
                    "jr $ra\n";

    @Test
    void simpleFunDeclaration() {

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                return "f_entry";
            }
        };

        StmtBlock mainBlock = GetAST(
                "{" +
                    "    f(int x){" +
                    "    }" +
                    "}");
        String expected =
                OpenScopeWithVars(0) +
                "f_entry:\n" +
                "move $fp $sp\n" +
                "push $ra\n" +
                "$ra <- top\n" +
                "addi $sp $sp 4\n" +
                "$fp <- top\n" +
                "pop\n" +
                "jr $ra\n" +
                CloseScopeWithVars(0);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void simpleFunDeclarationAndCall() {

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                return "f_entry";
            }
        };

        StmtBlock mainBlock = GetAST(
                "{" +
                    "    f(int x){" +
                    "    }" +
                    "    f(42);" +
                    "}");
        String expected =
                OpenScopeWithVars(0) +
                        F_DECLARATION +
                        "push $fp\n" +
                        "li $a0 42\n" +
                        "push $a0\n" +
                        "jal f_entry\n" +
                CloseScopeWithVars(0);

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }
}
