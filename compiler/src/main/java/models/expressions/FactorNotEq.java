package models.expressions;

import util.Strings;

import static util.Strings.*;
import static util.Strings.ACC;

public class FactorNotEq extends Factor {

    public FactorNotEq(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String notEqual = Strings.getFreshLabel();
        String exit = getFreshLabel();
        return
                getLeft().codeGeneration() +
                push(ACC) +
                getRight().codeGeneration() +
                assignTop(TMP) +
                pop() +
                beq(ACC,TMP,notEqual) +
                loadI(ACC, "1") +
                b(exit)+
                notEqual+":\n" +
                loadI(ACC, "0") +
                exit +":\n";
    }
}
