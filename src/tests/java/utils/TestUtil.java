package utils;

import compilermodels.Environment;
import compilermodels.VisitorImpl;
import compilermodels.expressions.Exp;
import compilermodels.statements.StmtBlock;
import interpretermodels.Assembly;
import interpretermodels.CVMVisitorImpl;
import interpretermodels.InterpreterEnv;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.function.Executable;
import parser.*;
import util.SemanticError;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static util.Strings.*;

public class TestUtil {

    public static String OpenScopeWithVars(int numVariables){
        StringBuilder result = new StringBuilder();
        result.append(push(FP));
        for (int i = 0; i < numVariables; i++) {
            result.append(push(TMP));
        }
        result.append(push(FP));
        result.append(move(FP,SP));
        return result.toString();
    }

    public static String CloseScopeWithVars(int numVariables){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numVariables; i++) {
            result.append(pop());
        }
        result.append(pop());
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

    public static List<Integer> GetExecutionPrintsForFile(String file, boolean shouldPrintCgen){
        StmtBlock mainBlock = GetAST(file);
        String result = mainBlock.codeGeneration();
        if(shouldPrintCgen) {
            System.out.println(result);
        }
        CharStream is = CharStreams.fromString(result);
        CVMLexer lexer = new CVMLexer(is);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CVMParser parser = new CVMParser(tokens);
        CVMVisitorImpl visitor = new CVMVisitorImpl();
        InterpreterEnv e = new InterpreterEnv();
        Assembly assembly = visitor.visitAssembly(parser.assembly());
        assembly.loadCode(e);
        ExecuteVM vm = new ExecuteVM(e.code);
        vm.cpu();
        return vm.getPrintedResults();
    }

    public static ArrayList<SemanticError> GetSemanticsErrors(String string){
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
