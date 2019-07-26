package semantic;


import org.junit.jupiter.api.Test;
import util.SemanticError;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.Strings.ERROR_VARIABLE_NOT_INITIALIZED;
import static util.TestUtil.GetSemanticsErrors;

public class VarDeclarationErrorsTests {


    @Test
    void CheckSemantics_ShouldReturnError_WithRecursiveVariableDeclaration() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "   int x = x + 1;" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(ERROR_VARIABLE_NOT_INITIALIZED + "x", errors.get(0).toString());
    }

    @Test
    void CheckSemantics_ShouldPrint_WithRecursiveAssignament() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "   int x = 1;" +
                        "   x = x + 1;" +
                        "}");
        assertEquals(0, errors.size());
    }

    @Test
    void CheckSemantics_ShouldPrint_WithVarDeclarationAndAssignament() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "   int x = 1;" +
                        "   int y = x + 1;" +
                        "}");
        assertEquals(0, errors.size());
    }
}
