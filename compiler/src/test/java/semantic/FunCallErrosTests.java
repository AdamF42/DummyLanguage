package semantic;

import org.junit.jupiter.api.Test;
import util.SemanticError;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.Strings.ERROR_GLOBAL_VAR_AS_PARAMETER;
import static util.TestUtil.GetSemanticsErrors;

class FunCallErrosTests {


    @Test
    void CheckSemantics_ShouldReturnError_WithFunctionCalledWithGlobalVarAsParamWithDelete() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "    int a = 10;" +
                        "    f(var int b) {" +
                        "        delete a ;" +
                        "        b = b + 3;" +
                        "        print b;" +
                        "    }" +
                        "    f(a);" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(ERROR_GLOBAL_VAR_AS_PARAMETER + "a", errors.get(0).toString());
    }

    @Test
    void CheckSemantics_ShouldReturnError_WithFunctionCalledWithGlobalVarAsParamWithAccess() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "    int a = 10;" +
                        "    f(var int b) {" +
                        "        a = a + 1;" +
                        "        print a;" +
                        "        b = b + 3;" +
                        "        print b;" +
                        "    }" +
                        "    f(a);" +
                        "    print a;" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(ERROR_GLOBAL_VAR_AS_PARAMETER + "a", errors.get(0).toString());
    }

}
