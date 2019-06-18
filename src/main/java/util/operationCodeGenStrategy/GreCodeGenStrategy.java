package util.operationCodeGenStrategy;

import models.expressions.Exp;
import util.Strings;

import static util.Strings.*;
import static util.Strings.ACC;

public class GreCodeGenStrategy implements OpCodeGenStrategy {
    @Override
    public String GetCodeForOperator(Exp right) {
        String greaterOrEqual = Strings.GetFreshLabel();
        String exit = GetFreshLabel();
        return
                push(ACC) +
                right.codeGeneration() +
                assignTop(TMP) +
                pop() +
                bgre(ACC,TMP,greaterOrEqual) +
                loadI(ACC, "0") +
                b(exit)+
                greaterOrEqual+":\n" +
                loadI(ACC, "1") +
                exit +":\n";
    }
}
