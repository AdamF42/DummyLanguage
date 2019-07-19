package models.compiler.expressions;


import models.compiler.types.Type;
import util.TypeCheckException;
import util.TypeUtils;

public abstract class Term extends Exp {

    public Term(Exp left, Exp right) {
        super(left, right);
    }

    @Override
    public Type typeCheck() throws TypeCheckException {
        if (getRight() != null){
            TypeUtils.typeCheck(getLeft().typeCheck(),getRight());
        }
        return this.getLeft().typeCheck();
    }

}
