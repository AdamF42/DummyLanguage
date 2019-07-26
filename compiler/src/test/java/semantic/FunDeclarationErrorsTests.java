package semantic;

import org.junit.jupiter.api.Test;
import util.SemanticError;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.Strings.*;
import static util.TestUtil.GetSemanticsErrors;

class FunDeclarationErrorsTests {


    @Test
    void CheckSemantics_ShouldReturnError_WithFunctionCalledAsVariable() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "    int f = 0;" +
                        "    f(int x){ }" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(ERROR_ALREADY_DECLARED_IDENTIFIER + "f", errors.get(0).toString());
    }

}
