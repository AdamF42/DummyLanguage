package util;

import models.expressions.Exp;

import static util.Strings.*;

public class MultCodeGenStrategy implements OpCodeGenStrategy {

    @Override
    public String GetCodeForOperator(Exp right) {
        return push(ACC) +
                right.codeGeneration() +
                assignTop(TMP) +
                mult(ACC,ACC,TMP) +
                pop();
    }
}
