package models.compiler.expressions;

import static util.Strings.*;
import static util.Strings.pop;

public class TermDiv extends Term {

    public TermDiv(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        return
                getLeft().codeGeneration() +
                push(ACC) +
                getRight().codeGeneration() +
                assignTop(TMP) +
                div(ACC,ACC,TMP) +
                pop();
    }
}
