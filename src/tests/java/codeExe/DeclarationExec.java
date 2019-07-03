package codeExe;


import mockit.Mock;
import mockit.MockUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Strings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestUtil.GetExecutionPrintsForFile;

class DeclarationExec {
    private static int label_count;

    @BeforeEach
    void setUp() {
        label_count = 0;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void DecWithAddOp_ShouldPrint_Expected() {

        List<Integer> actual = GetExecutionPrintsForFile("" +
                "{ " +
                "   int x = 3; " +
                "   print x;" +
                "}", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(3);

        assertEquals(expected,actual);
    }

    @Test
    void DecWithBasicFourOp_ShouldPrint_Expected() {

        List<Integer> actual = GetExecutionPrintsForFile("" +
                "{ " +
                "   int x = 3 + 3; " +
                "   print x;" +
                "   int y = 3 - 3;" +
                "   print y;" +
                "   int z = 3 * 3; " +
                "   print z;" +
                "   int w = 3 / 3; " +
                "   print w;" +
                "}", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(6);
        expected.add(0);
        expected.add(9);
        expected.add(1);

        assertEquals(expected,actual);
    }

    @Test
    void DecWithAnd_ShouldPrint_Expected() {
        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "xlabel");
        labels.put(2, "ylabel");
        labels.put(3, "zlabel");
        labels.put(4, "wlabel");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };
        List<Integer> actual = GetExecutionPrintsForFile("" +
                "{ " +
                "   bool x = true && true; " +
                "   print x;" +
                "   bool y = true && false; " +
                "   print y;" +
                "   bool z = false && true; " +
                "   print z;" +
                "   bool w = false && false; " +
                "   print w;" +
                "}", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(0);
        expected.add(0);
        expected.add(0);
        assertEquals(expected,actual);
    }

    @Test
    void DecWithOr_ShouldPrint_Expected() {
        HashMap<Integer,String> labels = new HashMap<>();
        labels.put(1, "xlabel");
        labels.put(2, "ylabel");
        labels.put(3, "zlabel");
        labels.put(4, "wlabel");

        new MockUp<Strings>() {
            @Mock
            public String GetFreshLabel() {
                label_count ++;
                return labels.get(label_count);
            }
        };
        List<Integer> actual = GetExecutionPrintsForFile("" +
                "{ " +
                "   bool x = true || true; " +
                "   print x;" +
                "   bool y = true || false; " +
                "   print y;" +
                "   bool z = false || true; " +
                "   print z;" +
                "   bool w = false || false; " +
                "   print w;" +
                "}", false);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(1);
        expected.add(1);
        expected.add(0);
        assertEquals(expected,actual);
    }

}
