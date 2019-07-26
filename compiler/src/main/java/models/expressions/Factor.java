package models.expressions;


import models.types.Type;
import models.types.Bool;
import exeptions.TypeCheckException;


public abstract class Factor extends Term {


    public Factor(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public Type typeCheck() throws TypeCheckException {
        return new Bool();
    }

}
