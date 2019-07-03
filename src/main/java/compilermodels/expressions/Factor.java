package compilermodels.expressions;


import compilermodels.types.Type;
import compilermodels.types.TypeBool;
import util.TypeCheckError;


public abstract class Factor extends Term {


    public Factor(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        return new TypeBool();
    }

}
