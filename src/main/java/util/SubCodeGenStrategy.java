package util;

import models.expressions.Exp;

import static util.Strings.*;

public class SubCodeGenStrategy implements OpCodeGenStrategy {

    @Override
    public String GetCodeForOperator(Exp right) {
        return push(ACC) +
                right.codeGeneration() +
                assignTop(TMP) +
                sub(ACC,ACC,TMP) +
                pop();
    }
}
