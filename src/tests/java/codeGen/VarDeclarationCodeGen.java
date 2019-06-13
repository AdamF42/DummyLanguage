package codeGen;

import models.Environment;
import models.VisitorImpl;
import models.statements.StmtBlock;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclarationCodeGen {

    private StmtBlock getAST(String file){
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

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void varDeclaration() {
        StmtBlock mainBlock = getAST("{\n int x = 3;\n  }");
        String expected =
                "li $a0 3\n" +
                "sw $a0 4($fp)\n"; // at 0 ($fp) is located the old frame pointer

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDeclarations() {
        StmtBlock mainBlock = getAST("{\n int x = 3;\n int y = 5;  }");
        String expected =
                "li $a0 3\n" +
                "sw $a0 4($fp)\n" +
                "li $a0 5\n" +
                "sw $a0 8($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithNumberAdd() {
        StmtBlock mainBlock = getAST("{\n int x = 3 + 1;\n }");
        String expected =
                "li $a0 3\n" +
                "move $t1 $a0\n" +
                "li $a0 1\n" +
                "add $a0 $a0 $t1\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberAdd() {
        StmtBlock mainBlock = getAST("{\n int x = x + 1;\n }");
        String expected =
                "lw $a0 4($fp)\n" +
                "move $t1 $a0\n" +
                "li $a0 1\n" +
                "add $a0 $a0 $t1\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithNumberSub() {
        StmtBlock mainBlock = getAST("{\n int x = 3 - 1;\n }");
        String expected =
                "li $a0 3\n" +
                "move $t1 $a0\n" +
                "li $a0 1\n" +
                "sub $a0 $a0 $t1\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberSub() {
        StmtBlock mainBlock = getAST("{\n int x = x - 1;\n }");
        String expected =
                "lw $a0 4($fp)\n" +
                "move $t1 $a0\n" +
                "li $a0 1\n" +
                "sub $a0 $a0 $t1\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberMult() {
        StmtBlock mainBlock = getAST("{\n int x = x * 2;\n }");
        String expected =
                "lw $a0 4($fp)\n" +
                "move $t1 $a0\n" +
                "li $a0 2\n" +
                "mult $a0 $a0 $t1\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithVariableAndNumberDiv() {
        StmtBlock mainBlock = getAST("{\n int x = x / 2;\n }");
        String expected =
                "lw $a0 4($fp)\n" +
                "move $t1 $a0\n" +
                "li $a0 2\n" +
                "div $a0 $a0 $t1\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecsWithComplexExp() {
        StmtBlock mainBlock = getAST("{ int y = 6; int x = (y+1)*((x-1) / 2); }");
        //TODO: not sure if it is correct...check it on paper...
        String expected =
                "li $a0 6\n" +
                "sw $a0 4($fp)\n" +
                "lw $a0 4($fp)\n" +
                "move $t1 $a0\n" +
                "li $a0 1\n" +
                "add $a0 $a0 $t1\n" +
                "move $t1 $a0\n" +
                "lw $a0 8($fp)\n" +
                "move $t1 $a0\n" +
                "li $a0 1\n" +
                "sub $a0 $a0 $t1\n" +
                "move $t1 $a0\n" +
                "li $a0 2\n" +
                "div $a0 $a0 $t1\n" +
                "mult $a0 $a0 $t1\n" +
                "sw $a0 8($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithSimpleBooleanAssignment() {
        StmtBlock mainBlock = getAST("{ bool x = true; }");
        String expected =
                "li $a0 1\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithBooleanExpressionAnd() {
        StmtBlock mainBlock = getAST("{ bool x = true && false; }");
        //TODO: not sure if it is correct...check it on paper...
        String expected =
                "li $a0 1\n" +
                "move $t1 $a0\n" +
                "li $a0 0\n" +
                "end = newLabel()\n" +
                "beq $a0 0 end\n" +
                "li $a0 0\n" +
                "end:\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

    @Test
    void varDecWithBooleanExpressionOr() {
        StmtBlock mainBlock = getAST("{ bool x = true || false; }");
        //TODO: not sure if it is correct...check it on paper...

        String expected =
                "li $a0 1\n" +
                "move $t1 $a0\n" +
                "li $a0 0\n" +
                "end = newLabel()\n" +
                "beq $a0 1 end\n" +
                "li $a0 0\n" +
                "end:\n" +
                "sw $a0 4($fp)\n";

        String result = mainBlock.codeGeneration();
        assertEquals(expected,result);
    }

}