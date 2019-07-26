package codeExe;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static codeExe.TestUtil.GetExecutionPrintsForFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IfThenElseExecutionTests {

    @Test
    void ifThenElseEqCondition_ShouldExecuteThenBranch_WithTrueCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x==42 ) then{ print 42; } else { print x+1; } }", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseEqCondition_ShouldExecuteElseBranch_WithFalseCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x==41 ) then{ print x; } else { print x+8; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(50);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseNotEqCondition_ShouldExecuteThenBranch_WithTrueCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x != 0 ) then{ print x; } else { print x+1; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseNotEqCondition_ShouldExecuteElseBranch_WithFalseCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x != 42 ) then{ print x; } else { print x+1; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(43);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseGrCondition_ShouldExecuteThenBranch_WithTrueCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x > 0 ) then{ print x; } else { print x+8; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseGrCondition_ShouldExecuteElseBranch_WithFalseCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x > 42 ) then{ print x; } else { print x+1; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(43);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseGreCondition_ShouldExecuteThenBranch_WithTrueCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x >= 41 ) then{ print x; } else { print x+8; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseGreCondition2_ShouldExecuteThenBranch_WithTrueCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x >= 42 ) then{ print x; } else { print x+8; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseGreCondition_ShouldExecuteElseBranch_WithFalseCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x >= 43 ) then{ print x; } else { print x+8; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(50);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseLrCondition_ShouldExecuteElseBranch_WithTrueCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x < 41 ) then{ print x; } else { print x+1; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseLrCondition_ShouldExecuteElseBranch_WithFalseCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x < 42 ) then{ print x; } else { print x+1; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(43);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseLreCondition_ShouldExecuteThenBranch_WithTrueCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x <= 42 ) then{ print x; } else { print x+1; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseLreCondition2_ShouldExecuteThenBranch_WithTrueCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x <= 43 ) then{ print x; } else { print x+1; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void ifThenElseLreCondition_ShouldExecuteElseBranch_WithFalseCondition() {

        List<Integer> actual = GetExecutionPrintsForFile("{ int x = 42; if( x <= 41 ) then{ print x; } else { print x+8; } }", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(50);

        assertEquals(expected,actual);
    }
}
