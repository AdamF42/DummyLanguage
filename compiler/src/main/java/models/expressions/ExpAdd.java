package models.expressions;


import static util.Strings.*;
import static util.Strings.pop;


public class ExpAdd extends Exp {

    public ExpAdd(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        return getLeft().codeGeneration() +
                push(ACC) +
                getRight().codeGeneration() +
                assignTop(TMP) +
                add(ACC,ACC,TMP) +
                pop();
    }
}
