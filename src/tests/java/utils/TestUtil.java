package utils;

import com.sun.org.apache.regexp.internal.RESyntaxException;
import models.Environment;
import models.VisitorImpl;
import models.statements.StmtBlock;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.function.Executable;
import parser.ComplexStaticAnalysisLexer;
import parser.ComplexStaticAnalysisParser;
import util.SemanticError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static util.Strings.*;

public class TestUtil {

    public static String OpenScopeWithVars(int numVariables){
        StringBuilder result = new StringBuilder();
        result.append(push(FP));
        result.append(loadI(TMP,"0"));
        for (int i = 0; i < numVariables; i++) {
            result.append(push(TMP));
        }
        result.append(move(FP,SP));
        return result.toString();
    }


    public static String CloseScopeWithVars(int numVariables){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numVariables; i++) {
            result.append(pop());
        }
        result.append(assignTop(FP));
        result.append(pop());
        return result.toString();
    }

    public static StmtBlock getAST(String file){
        CharStream is = CharStreams.fromString(file);
        ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(is);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);
        VisitorImpl visitor = new VisitorImpl();
        Environment e = new Environment();
        StmtBlock mainBlock = visitor.visitBlock(parser.block());
        assertNotNull(mainBlock);
        List<SemanticError> errors =  mainBlock.checkSemantics(e);
        assertEquals(0, errors.size());
        assertDoesNotThrow((Executable) mainBlock::typeCheck);
        return mainBlock;
    }

}
