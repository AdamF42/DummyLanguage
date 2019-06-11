package models.expressions;

import util.Strings;

import static util.Strings.*;

public class FactorAnd extends Factor {

    public FactorAnd(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String endLabel = Strings.GetFreshLabel();
        return  getLeft().codeGeneration() +
                loadI(TMP, "0") +
                beq(ACC,TMP,endLabel) +
                getRight().codeGeneration() +
                endLabel+":\n";
    }
}
