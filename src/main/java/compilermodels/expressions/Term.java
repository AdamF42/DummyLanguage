package compilermodels.expressions;


import compilermodels.types.Type;
import util.TypeCheckError;
import util.TypeUtils;

public abstract class Term extends Exp {

    public Term(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        if (getRight() != null){
            TypeUtils.typeCheck(getLeft().typeCheck(),getRight());
        }
        return this.getLeft().typeCheck();
    }

}
