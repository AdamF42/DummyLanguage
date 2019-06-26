package models.expressions;

import util.Strings;

import static util.Strings.*;
import static util.Strings.ACC;

public class FactorEq extends Factor {

    public FactorEq(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String equal = Strings.GetFreshLabel();
        String exit = GetFreshLabel();
        return
                getLeft().codeGeneration() +
                push(ACC) +
                getRight().codeGeneration() +
                assignTop(TMP) +
                pop() +
                beq(ACC,TMP,equal) +
                loadI(ACC, "0") +
                b(exit)+
                equal+":\n" +
                loadI(ACC, "1") +
                exit +":\n";
    }
}
