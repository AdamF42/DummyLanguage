package util.operationCodeGenStrategy;

import models.expressions.Exp;
import util.Strings;

import static util.Strings.*;
import static util.Strings.ACC;

public class LrCodeGenStrategy implements OpCodeGenStrategy {
    @Override
    public String GetCodeForOperator(Exp right) {
        String lower = Strings.GetFreshLabel();
        String exit = GetFreshLabel();
        return
                push(ACC) +
                right.codeGeneration() +
                assignTop(TMP) +
                pop() +
                blr(ACC,TMP,lower) +
                loadI(ACC, "0") +
                b(exit)+
                lower+":\n" +
                loadI(ACC, "1") +
                exit +":\n";
    }
}
