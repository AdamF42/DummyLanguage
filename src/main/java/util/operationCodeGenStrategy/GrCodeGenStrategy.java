package util.operationCodeGenStrategy;

import models.expressions.Exp;
import util.Strings;

import static util.Strings.*;
import static util.Strings.ACC;

public class GrCodeGenStrategy implements OpCodeGenStrategy {
    @Override
    public String GetCodeForOperator(Exp right) {
        String greater = Strings.GetFreshLabel();
        String exit = GetFreshLabel();
        return
                push(ACC) +
                right.codeGeneration() +
                assignTop(TMP) +
                pop() +
                bgr(ACC,TMP,greater) +
                loadI(ACC, "0") +
                b(exit)+
                greater+":\n" +
                loadI(ACC, "1") +
                exit +":\n";
    }
}
