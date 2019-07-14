package models.compiler.expressions;


import static util.Strings.*;


public class FactorEq extends Factor {

    public FactorEq(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public String codeGeneration() {
        String equal = getFreshLabel();
        String exit = getFreshLabel();
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
