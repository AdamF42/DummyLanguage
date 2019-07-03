package models.compiler.expressions;

import util.Strings;

import static util.Strings.*;

public class FactorOr extends Factor {

    public FactorOr(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String endLabel = Strings.GetFreshLabel();
        return  getLeft().codeGeneration() +
                loadI(TMP,"1") +
                beq(ACC,TMP,endLabel) +
                getRight().codeGeneration() +
                endLabel+":\n";
    }
}
