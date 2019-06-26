package models.expressions;

import static util.Strings.*;
import static util.Strings.pop;

public class ExpSub extends Exp {
    public ExpSub(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        return  getLeft().codeGeneration() +
                push(ACC) +
                getRight().codeGeneration() +
                assignTop(TMP) +
                sub(ACC,ACC,TMP) +
                pop();
    }
}
