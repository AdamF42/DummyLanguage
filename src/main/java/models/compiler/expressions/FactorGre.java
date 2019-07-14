package models.compiler.expressions;

import util.Strings;

import static util.Strings.*;
import static util.Strings.ACC;

public class FactorGre extends Factor {

    public FactorGre(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String greaterOrEqual = Strings.getFreshLabel();
        String exit = getFreshLabel();
        return
                getLeft().codeGeneration() +
                push(ACC) +
                getRight().codeGeneration() +
                assignTop(TMP) +
                pop() +
                bgre(ACC,TMP,greaterOrEqual) +
                loadI(ACC, "0") +
                b(exit)+
                greaterOrEqual+":\n" +
                loadI(ACC, "1") +
                exit +":\n";
    }
}
