package util;

import models.expressions.Exp;

import static util.Strings.*;

public class AddCodeGenStrategy implements OpCodeGenStrategy {

    @Override
    public String GetCodeForOperator(Exp right) {
        return push(ACC) +
                right.codeGeneration() +
                assignTop(TMP) +
                add(ACC,ACC,TMP) +
                pop();
    }
}
