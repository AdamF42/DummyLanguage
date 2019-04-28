package models;

import util.TypeUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BinaryOp extends Value {

    private ElementBase left;
    private ElementBase right;


    public BinaryOp(ElementBase left, ElementBase right ){
        this.left = left;
        this.right = right;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        if (right != null){
            TypeUtils.typeCheck(left.typeCheck(),right);
        }
        return this.left.typeCheck();
    }

    @Override
    public List<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<>(left.checkSemantics(env));

        if (right != null) {
            res.addAll(right.checkSemantics(env));
        }

        return res;
    }

    public ElementBase getLeft() {
        return left;
    }

    public ElementBase getRight() {
        return right;
    }
}
