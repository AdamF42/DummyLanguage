package models.expressions;


import models.types.Type;
import models.types.TypeBool;
import util.Strings;
import util.TypeCheckError;

import static util.Strings.*;


public abstract class Factor extends Term {


    public Factor(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        return new TypeBool();
    }

}
