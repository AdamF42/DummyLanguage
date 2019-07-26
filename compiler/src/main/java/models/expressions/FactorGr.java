package models.expressions;

import util.Strings;

import static util.Strings.*;
import static util.Strings.ACC;

public class FactorGr extends Factor {

    public FactorGr(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String greater = Strings.getFreshLabel();
        String exit = getFreshLabel();
        return
                getLeft().codeGeneration() +
                push(ACC) +
                getRight().codeGeneration() +
                assignTop(TMP) +
                pop() +
                bgr(ACC,TMP,greater) +
                loadI(ACC, "0") +
                b(exit)+
                greater+":\n" +
                loadI(ACC, "1") +
                exit +":\n";
    }
}
