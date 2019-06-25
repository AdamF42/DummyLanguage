package models.expressions;

import static util.Strings.*;
import static util.Strings.pop;

public class TermMult extends Term {

    public TermMult(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        return
                getLeft().codeGeneration() +
                push(ACC) +
                getRight().codeGeneration() +
                assignTop(TMP) +
                mult(ACC,ACC,TMP) +
                pop();
    }
}
