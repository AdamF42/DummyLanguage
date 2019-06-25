package models.expressions;

import util.Strings;

import static util.Strings.ACC;
import static util.Strings.beq;

public class FactorAnd extends Factor {

    public FactorAnd(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String endLabel = Strings.GetFreshLabel();
        return  getLeft().codeGeneration() +
                beq(ACC,"0",endLabel) +
                getRight().codeGeneration() +
                endLabel+":\n";
    }
}
