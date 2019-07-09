package models.compiler.expressions;


import models.compiler.types.Type;
import models.compiler.types.TypeBool;
import util.TypeCheckException;


public abstract class Factor extends Term {


    public Factor(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public Type typeCheck() throws TypeCheckException {
        return new TypeBool();
    }

}
