package codeExe;

import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import util.Strings;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.*;


@Disabled("Not ready yet. Explicitly Disabled")
class FunctionExec {

    @BeforeEach
    void setUp() {
        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                return "fEntry";
            }
        };
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void simpleFunDeclarationAndCall() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{" +
                        "    f(int x){" +
                        "       print x;" +
                        "    }" +
                        "    f(42);" +
                        "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }
}
