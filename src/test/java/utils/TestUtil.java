package utils;

import models.compiler.Environment;
import models.compiler.VisitorImpl;
import models.compiler.statements.StmtBlock;
import models.interpreter.instructions.Assembly;
import models.interpreter.instructions.CVMVisitorImpl;
import models.interpreter.ExecuteVM;
import models.interpreter.CodeMemory;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.function.Executable;
import parser.*;
import util.ExecutionException;
import util.SemanticError;
import java.util.ArrayList;
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
        CodeMemory e = new CodeMemory();
        Assembly assembly = visitor.visitAssembly(parser.assembly());
        assembly.loadCode(e);
        ExecuteVM vm = new ExecuteVM(e.code);
        try {
            vm.cpu();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

        return vm.getPrintedResults();
    }

    public static List<Integer> GetExecutionPrintsForBytecode(String bytecode){

        CharStream is = CharStreams.fromString(bytecode);
        CVMLexer lexer = new CVMLexer(is);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CVMParser parser = new CVMParser(tokens);
        CVMVisitorImpl visitor = new CVMVisitorImpl();
        CodeMemory e = new CodeMemory();
        Assembly assembly = visitor.visitAssembly(parser.assembly());
        assembly.loadCode(e);
        ExecuteVM vm = new ExecuteVM(e.code);
        try {
            vm.cpu();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }

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
