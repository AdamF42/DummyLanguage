package models.expressions;

import util.Strings;

import static util.Strings.ACC;
import static util.Strings.beq;

public class FactorOr extends Factor {

    public FactorOr(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String endLabel = Strings.GetFreshLabel();
        return  getLeft().codeGeneration() +
                beq(ACC,"1",endLabel) +
                getRight().codeGeneration() +
                endLabel+":\n";
    }
}
