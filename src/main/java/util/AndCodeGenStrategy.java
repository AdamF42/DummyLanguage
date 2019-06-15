package util;

import models.expressions.Exp;

import static util.Strings.ACC;
import static util.Strings.beq;

public class AndCodeGenStrategy implements OpCodeGenStrategy {

    @Override
    public String GetCodeForOperator(Exp right) {
        return Strings.GetFreshLabel("end")+
                beq(ACC,"0","end") +
                right.codeGeneration() +
                "end:\n";
    }
}
