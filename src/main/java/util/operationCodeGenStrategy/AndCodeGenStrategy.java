package util.operationCodeGenStrategy;

import models.expressions.Exp;
import util.Strings;

import static util.Strings.ACC;
import static util.Strings.beq;

public class AndCodeGenStrategy implements OpCodeGenStrategy {

    @Override
    public String GetCodeForOperator(Exp right) {
        String endLabel = Strings.GetFreshLabel();
        return  beq(ACC,"0",endLabel) +
                right.codeGeneration() +
                endLabel+":\n";
    }
}
