package codeExe;

import exeptions.ExecutionException;
import models.CodeMemory;
import models.Environment;
import models.ExecuteVM;
import models.VisitorImpl;
import models.instructions.Assembly;
import models.instructions.CVMVisitorImpl;
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

public class TestUtil {

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
        interpreter.parser.CVMLexer lexer = new interpreter.parser.CVMLexer(is);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        interpreter.parser.CVMParser parser = new interpreter.parser.CVMParser(tokens);
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
}
