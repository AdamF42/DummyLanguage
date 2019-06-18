package util.operationCodeGenStrategy;

import models.expressions.Exp;
import util.Strings;

import static util.Strings.*;
import static util.Strings.ACC;

public class NotEqCodeGenStrategy implements OpCodeGenStrategy {
    @Override
    public String GetCodeForOperator(Exp right) {
        String notEqual = Strings.GetFreshLabel();
        String exit = GetFreshLabel();
        return
                push(ACC) +
                right.codeGeneration() +
                assignTop(TMP) +
                pop() +
                beq(ACC,TMP,notEqual) +
                loadI(ACC, "1") +
                b(exit)+
                notEqual+":\n" +
                loadI(ACC, "0") +
                exit +":\n";
    }
}
