package util.operationCodeGenStrategy;

import models.expressions.Exp;
import util.Strings;

import java.util.SplittableRandom;

import static util.Strings.*;

public class EqCodeGenStrategy implements OpCodeGenStrategy {
    @Override
    public String GetCodeForOperator(Exp right) {
        String equal = Strings.GetFreshLabel();
        String exit = GetFreshLabel();
        return
                push(ACC) +
                right.codeGeneration() +
                assignTop(TMP) +
                pop() +
                beq(ACC,TMP,equal) +
                loadI(ACC, "0") +
                b(exit)+
                equal+":\n" +
                loadI(ACC, "1") +
                exit +":\n";
    }
}
