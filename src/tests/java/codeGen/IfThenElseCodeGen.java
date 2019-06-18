package codeGen;

import models.statements.StmtBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.getAST;

public class IfThenElseCodeGen {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void assignmentInTheSameScope() {
        StmtBlock mainBlock = getAST("{ int x = 1; if(x==1)then{x=2;}else{x=3;} }");
        //TODO: to complete
        String expected =
                "push $fp\n" +
                "push $al\n" +
                "move $fp $sp\n" +
                "li $a0 1\n" +
                "sw $a0 0($fp)\n" +
                "li $a0 3\n" +
                "lw $al 0($fp)\n" +
                "sw $a0 0($al)\n" +
                "$al <- top\n" +
                "pop\n" +
                "$fp <- top\n" +
                "pop\n";

//        String result = mainBlock.codeGeneration();
//        assertEquals(expected,result);
    }


}
