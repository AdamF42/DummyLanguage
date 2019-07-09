package models.compiler.expressions;

import util.Strings;

import static util.Strings.*;
import static util.Strings.ACC;

public class FactorLre extends Factor {

    public FactorLre(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String lower = Strings.GetFreshLabel();
        String exit = GetFreshLabel();
        return
                getLeft().codeGeneration() +
                push(ACC) +
                getRight().codeGeneration() +
                assignTop(TMP) +
                pop() +
                blre(ACC,TMP,lower) +
                loadI(ACC, "0") +
                b(exit)+
                lower+":\n" +
                loadI(ACC, "1") +
                exit +":\n";
    }
}
