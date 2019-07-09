package codeExe;

import mockit.Mock;
import mockit.MockUp;
import models.compiler.statements.StmtBlock;
import models.interpreter.CodeMemory;
import models.interpreter.ExecuteVM;
import models.interpreter.instructions.Assembly;
import models.interpreter.instructions.CVMVisitorImpl;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.CVMLexer;
import parser.CVMParser;
import util.ExecutionException;
import util.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.TestUtil.*;


class FunctionExec {
    private static int label_count;
    private static final HashMap<Integer, String> labels = new HashMap<>();
    static {
        labels.put(1, "fEntry");
        labels.put(2, "skipFunctionDeclaration");
        labels.put(3, "ifEnd");
        labels.put(4, "elseBranch");
        labels.put(5, "Equal");
        labels.put(6, "conditionEnd");
    }

    @BeforeEach

    void setUp() {
        label_count = 0;
        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };
    }

    @Test
    void FunctionOneParamPrint_ShouldPrintExpected_WithIntegerIn() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{" +
                        "    f(int x){" +
                        "       print x;" +
                        "    }" +
                        "    f(42);" +
                        "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void FunctionOneParamPrint_ShouldPrint1_WithTrueBoolIn() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{" +
                        "    f(bool x){" +
                        "       print x;" +
                        "    }" +
                        "    f(true);" +
                        "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(1);

        assertEquals(expected,actual);
    }

    @Test
    void FunctionOneParamPrint_ShouldPrint0_WithFalseBoolIn() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{" +
                        "    f(bool x){" +
                        "       print x;" +
                        "    }" +
                        "    f(false);" +
                        "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(0);

        assertEquals(expected,actual);
    }

    @Test
    void FunctionParamPrint_ShouldPrintExpected_WithCopyParameter() {

        List<Integer> actual = GetExecutionPrintsForFile("" +
                        "{" +
                        "   int z = 0;" +
                        "   int y = 42;" +
                        "   f(int x){" +
                        "      print x;" +
                        "   }" +
                        "   f(y);" +
                        "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void FunctionParamPrint_ShouldPrintExpected_WithCopyParameters() {

        List<Integer> actual = GetExecutionPrintsForFile("" +
                "{" +
                "   int x = 42;" +
                "   int y = 100;" +
                "   int z = 163;" +
                "   f(int tizio, int caio, int sempronio){" +
                "      print sempronio;" +
                "      print caio;" +
                "      print tizio;" +
                "   }" +
                "   f(x, y, z);" +
                "}", false);



        List<Integer> expected = new ArrayList<>();
        expected.add(163);
        expected.add(100);
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void FunctionParamPrint_ShouldPrintExpected_WithReferenceParameter() {

        List<Integer> actual = GetExecutionPrintsForFile("" +
                "{" +
                "   int y = 41;" +
                "   f(var int x){" +
                "       x = x+1;" +
                "       print x;" +
                "   }" +
                "   f(y);" +
                "   print y;" +
                "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(42);
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void FunctionReferenceParam_ShouldPrintExpected_WithReferenceParameter() {
        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return "label"+String.valueOf(label_count);
            }
        };

        List<Integer> actual = GetExecutionPrintsForFile("" +
                "{" +
                "   int y = 41;" +
                "   f(var int x){" +
                "       g(var int x){" +
                "           x = x+1;" +
                "           print x;" +
                "       }" +
                "       g(x);" +
                "   }" +
                "   f(y);" +
                "   print y;" +
                "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(42);
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void Function_ShouldPrintExpected_WithReferenceParameter() {
        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return "label"+String.valueOf(label_count);
            }
        };

        List<Integer> actual = GetExecutionPrintsForFile("" +
                "{" +
                "   int a = 41;" +
                "   int w = 90;" +
                "   f(var int x, int y){" +
                "       x = x+1;" +
                "       g(int x, var int y){" +
                "           y = y+4;" +
                "       }" +
                "       print a;" +
                "       print w;" +
                "       g(x, w);" +
                "   }" +
                "   f(a,5);" +
                "   print a;" +
                "   print w;" +
                "}", false);

        List<Integer> expected = new ArrayList<>();
        // TODO: Giustifica nella relazione il perchè non stampa 42..l'update viene fatto dopo il ritorno della funzione...
        expected.add(41);
        expected.add(90);

        expected.add(42);
        expected.add(94);

        assertEquals(expected,actual);
    }

    @Test
    void RecursiveFunction_ShouldPrint42_WithActualCode() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{" +
                    "    int x = 42;" +
                    "    f(int y){" +
                    "        if (y == 0) then {" +
                    "            print(x);" +
                    "        } else {" +
                    "            f(y-1) ;" +
                    "        }" +
                    "    }" +
                    "    f(10) ;" +
                    "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(42);

        assertEquals(expected,actual);
    }

    @Test
    void RecursiveFunction_ShouldPrintCounter_WithActualCode() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{" +
                        "    int x = 42;" +
                        "    f(int y){" +
                        "        if (y == 0) then {" +
                        "        } else {" +
                        "            f(y-1) ;" +
                        "            print (y);" +
                        "        }" +
                        "    }" +
                        "    f(5) ;" +
                        "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        expected.add(5);

        assertEquals(expected,actual);
    }

    @Test
    void RecursiveFunction_ShouldPrintCounterReverseOrder_WithActualCode() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{" +
                        "    int x = 42;" +
                        "    f(int y){" +
                        "        if (y == 0) then {" +
                        "        } else {" +
                        "            print (y);" +
                        "            f(y-1) ;" +
                        "        }" +
                        "    }" +
                        "    f(5) ;" +
                        "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(5);
        expected.add(4);
        expected.add(3);
        expected.add(2);
        expected.add(1);

        assertEquals(expected,actual);
    }

    @Test
    void RecursiveFunction_ShouldPrintParamsSum_WithActualCode() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{\n" +
                    "    f(int m, int n){\n" +
                    "        if (m > n) then { " +
                    "           print(m+n) ;" +
                    "        }\n" +
                    "        else { " +
                    "           f(m+1,n+1) ; " +
                    "        }\n" +
                    "    }\n" +
                    "    f(5,4) ;\n" +
                    "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(9);


        assertEquals(expected,actual);
    }

    @Test
    void RecursiveFunction_ShouldGoOutOfMemory_WithActualCode() {

        StmtBlock mainBlock = GetAST("{\n" +
                "    f(int m, int n){\n" +
                "        if (m > n) then { " +
                "           print(m+n) ;" +
                "        }\n" +
                "        else { " +
                "           f(m+1,n+1) ; " +
                "        }\n" +
                "    }\n" +
                "    f(4,5) ;\n" +
                "}");
        String result = mainBlock.codeGeneration();
        CharStream is = CharStreams.fromString(result);
        CVMLexer lexer = new CVMLexer(is);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CVMParser parser = new CVMParser(tokens);
        CVMVisitorImpl visitor = new CVMVisitorImpl();
        CodeMemory e = new CodeMemory();
        Assembly assembly = visitor.visitAssembly(parser.assembly());
        assembly.loadCode(e);
        ExecuteVM vm = new ExecuteVM(e.code);
        Throwable exception = assertThrows(ExecutionException.class, vm::cpu);
        assertEquals(Strings.ERROR_OUT_OF_MEMORY, exception.getMessage());

    }

    @Test
    void RecursiveFunction_ShouldUpdateReferenceParam_WithActualCode() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{\n" +
                        "int b = 41;\n" +
                        "int s = 90;" +
                        "f (var int c, int b, var int x){\n" +
                        "   c = c + 1;" +
                        "   x = x + 4;" +
                        "   print c;\n" +
                        "   print x;\n" +
                        "}\n" +
                    "    {\n" +
                    "        f(b, 54, s);\n" +
                    "        print b;" +
                    "        print s;" +
                    "    }\n" +
                    "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(42);
        expected.add(94);
        expected.add(42);
        expected.add(94);

        assertEquals(expected,actual);
    }

    @Test
    void RecursiveFunction_ShouldUpdateReferenceParam_WithActualCode2() {

        List<Integer> actual = GetExecutionPrintsForFile(
                "{\n" +
                    "    int u = 1 ;\n" +
                    "    f(var int x, int n){\n" +
                    "        if (n == 0) then { print(x) ; }\n" +
                    "        else { int y = x*n ;  f(y,n-1) ; }\n" +
                    "        delete x ;\n" +
                    "    }\n" +
                    "    f(u,6) ;\n" +
                    "}", false);

        List<Integer> expected = new ArrayList<>();
        expected.add(720);

        assertEquals(expected,actual);
    }

}
