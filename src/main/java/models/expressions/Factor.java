package models.expressions;


import models.types.Type;
import models.types.TypeBool;
import util.Strings;
import util.TypeCheckError;

import static util.Strings.*;


public class Factor extends Term {


    public Factor(Exp left, Exp right, String op) {
        super(left, right, op);
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        Type result = super.typeCheck();
        if (super.getOp() != null) {
            result = new TypeBool();
        }
        return result;
    }

}
