package semantic;

import models.*;
import models.statements.StmtBlock;
import models.VisitorImpl;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import parser.ComplexStaticAnalysisLexer;
import parser.ComplexStaticAnalysisParser;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainAppTest {

    private String baseTestsRoot = "src/tests/samples/";

    private StmtBlock getAST(String fileName){
        try {
            CharStream is = CharStreams.fromFileName(fileName);
            ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(is);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);
            VisitorImpl visitor = new VisitorImpl();
            return visitor.visitBlock(parser.block());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void example_1() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_1.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(2, errors.size());
        assertEquals(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + "x", errors.get(0).toString());
        assertEquals(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + "z", errors.get(1).toString());
    }


    @Test
    void example_2() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_2.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + "z", errors.get(0).toString());
    }

    @Test
    void example_3() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_3.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_FUNCTION_DOESNT_EXIST + "f", errors.get(0).toString());
    }


    @Test
    void example_4() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_4.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_FUNCTION_DOESNT_EXIST + "f", errors.get(0).toString());
    }

    @Test
    void example_5() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_5.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + "x", errors.get(0).toString());
    }


    @Test
    void example_6() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_6.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void example_7() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_7.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        Throwable exception = assertThrows(TypeCheckError.class, mainBlock::typeCheck);
        assertEquals(Strings.ERROR_BEHAVIOR_MISMATCH, exception.getMessage());
    }

    @Test
    void example_8() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_8.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + "x", errors.get(0).toString());
    }

    @Test
    void example_9() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_9.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void example_10() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_10.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        Throwable exception = assertThrows(TypeCheckError.class, mainBlock::typeCheck);
        assertEquals(Strings.ERROR_BEHAVIOR_MISMATCH, exception.getMessage());
    }

    @Test
    void example_11() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_11.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void example_12() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_12.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + "z", errors.get(0).toString());
    }

    @Test
    void example_13() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_13.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_DANGEROUS_USE_OF_PARAMETER + "x", errors.get(0).toString());
    }

    @Test
    void example_14() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_14.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + "x", errors.get(0).toString());
    }

    @Test
    void example_15() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_15.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        Throwable exception = assertThrows(TypeCheckError.class, mainBlock::typeCheck);
        assertEquals(Strings.ERROR_BEHAVIOR_MISMATCH, exception.getMessage());
    }

    @Test
    void example_16() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_16.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void example_17() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_17.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + "f", errors.get(0).toString());

    }

    @Test
    void example_18() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_18.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_FUNCTION_DOESNT_EXIST + "g", errors.get(0).toString());
    }

    @Test
    void example_19() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_19.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        Throwable exception = assertThrows(TypeCheckError.class, mainBlock::typeCheck);
        assertEquals("ExpectedType var TypeBool, got TypeInt", exception.getMessage());
    }

    @Test
    void example_20() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_20.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        Throwable exception = assertThrows(TypeCheckError.class, mainBlock::typeCheck);
        assertEquals("ExpectedType var TypeInt, got TypeBool", exception.getMessage());
    }

    @Test
    void example_21() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_21.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        Throwable exception = assertThrows(TypeCheckError.class, mainBlock::typeCheck);
        assertEquals("ExpectedType var TypeInt, got TypeBool", exception.getMessage());
    }

    @Test
    void example_22() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_22.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        Throwable exception = assertThrows(TypeCheckError.class, mainBlock::typeCheck);
        assertEquals("ExpectedType var TypeBool, got TypeInt", exception.getMessage());
    }

    @Test
    void example_23() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_23.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void example_24() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_24.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(1, errors.size());
        assertEquals(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + "x", errors.get(0).toString());
    }

    @Test
    void example_25() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_25.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        Throwable exception = assertThrows(TypeCheckError.class, mainBlock::typeCheck);
        assertEquals(Strings.ERROR_BEHAVIOR_MISMATCH, exception.getMessage());
    }

    @Test
    void example_26() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_26.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void example_27() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_27.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        Throwable exception = assertThrows(TypeCheckError.class, mainBlock::typeCheck);
        assertEquals("ExpectedType: var TypeInt, got: right term TypeInt", exception.getMessage());
    }

    @Test
    void example_28() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_28.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void example_29() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "EXAMPLE_29.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void code_generation_1() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "code_generation_1.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void code_generation_2() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "code_generation_2.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    @Test
    void code_generation_3() {
        StmtBlock mainBlock = getAST(baseTestsRoot + "code_generation_3.spl");
        Environment e = new Environment();
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
    }

    // TODO: add tests for code generation -> TDD development

}