package util;

import models.expressions.Exp;

import static util.Strings.*;

public class DivCodeGenStrategy implements OpCodeGenStrategy {

    @Override
    public String GetCodeForOperator(Exp right) {
        return push(ACC) +
                right.codeGeneration() +
                assignTop(TMP) +
                div(ACC,ACC,TMP) +
                pop();
    }
}
