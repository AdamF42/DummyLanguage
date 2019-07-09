package semantic;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.SemanticError;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.Strings.*;
import static utils.TestUtil.*;

class DeletionErrors {


    @Test
    void CheckSemantics_ShouldReturnAnError_WithDoubleFunDeletion() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "    f( int a ){" +
                        "        a = 2;" +
                        "    } " +
                        "        delete f;" +
                        "        delete f;" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(ERROR_FUNCTION_HAS_BEEN_DELETED + "f", errors.get(0).toString());
    }

    @Test
    void CheckSemantics_ShouldReturnAnError_WithDoubleVarDeletion() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "   int x = 2;" +
                        "   delete x;" +
                        "   delete x;" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(ERROR_VARIABLE_HAS_BEEN_DELETED + "x", errors.get(0).toString());
    }

    @Test
    void CheckSemantics_ShouldReturnAnErrors_WithDoubleVarDeletionAndDoubleFunDeletion() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "    f( int a ){" +
                        "        delete a;" +
                        "        delete a;" +
                        "    } " +
                        "    f(2);" +
                        "    delete f;" +
                        "    delete f;" +
                        "}");
        assertEquals(2, errors.size());
        assertEquals(ERROR_VARIABLE_HAS_BEEN_DELETED + "a", errors.get(0).toString());
        assertEquals(ERROR_FUNCTION_HAS_BEEN_DELETED + "f", errors.get(1).toString());
    }

    @Test
    void CheckSemantics_ShouldReturnAnErrors_WithAlreadyDeclaredFunction() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "    f( int x ){" +
                        "    } " +
                        "    f( bool y ){" +
                        "    } " +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(ERROR_ALREADY_DECLARED_IDENTIFIER + "f", errors.get(0).toString());
    }

    @Test
    void CheckSemantics_ShouldReturnAnErrors_WithVariableNamedAsFunction() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "    f( int x ){" +
                        "    } " +
                        "    int f = 1;" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(ERROR_ALREADY_DECLARED_IDENTIFIER + "f", errors.get(0).toString());
    }

    @Test
    void CheckSemantics_ShouldReturnAnErrors_WithAlreadyDeclaredVariable() {
        List<SemanticError> errors = GetSemanticsErrors(
                "{" +
                        "    int a = 1;" +
                        "    int a = 1;" +
                        "}");
        assertEquals(1, errors.size());
        assertEquals(ERROR_ALREADY_DECLARED_IDENTIFIER + "a", errors.get(0).toString());
    }

}
