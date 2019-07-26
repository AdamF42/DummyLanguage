package util;

import models.Environment;
import models.VisitorImpl;
import models.statements.StmtBlock;
import parser.ComplexStaticAnalysisLexer;
import parser.ComplexStaticAnalysisParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.function.Executable;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static util.Strings.*;

public class TestUtil {

    public static String OpenScopeWithVars(int numVariables, boolean isMainBlock){

        StringBuilder result = new StringBuilder();
        result.append(push(FP));
        int varCounter = 0;
        for (int i = 0; i < numVariables; i++) {
            varCounter++;
        }
        if(numVariables>0)
            result.append(addi(SP,SP,String.valueOf(-varCounter*4)));
        result.append(push(FP));
        result.append(move(FP,SP));
        if(isMainBlock)
            result.append(storeW(FP,"0",FP));

        return result.toString();
    }

    public static String CloseScopeWithVars(int numVariables){

        StringBuilder result = new StringBuilder();
        int varCounter = 0;
        for (int i = 0; i < numVariables; i++) {
            varCounter++;
        }
        result.append(pop());
        if(numVariables>0)
            result.append(addi(SP,SP,String.valueOf(varCounter*4)));
        result.append(assignTop(FP));
        result.append(pop());

        return result.toString();
    }

    public static StmtBlock GetAST(String file){

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

    public static List<SemanticError> GetSemanticsErrors(String string){

        CharStream is = CharStreams.fromString(string);
        ComplexStaticAnalysisLexer lexer = new ComplexStaticAnalysisLexer(is);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ComplexStaticAnalysisParser parser = new ComplexStaticAnalysisParser(tokens);
        VisitorImpl visitor = new VisitorImpl();
        StmtBlock mainBlock = visitor.visitBlock(parser.block());
        Environment e = new Environment();
        assertNotNull(mainBlock);

        return mainBlock.checkSemantics(e);
    }
}
