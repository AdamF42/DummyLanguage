package models;

import util.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Simple Expression
 * Some child classes of this one will be SimpleExpSum, SimpleExpDiff,
 * SimpleExpDiv, SimpleExpMult and SimpleExpNeg
 *
 * @author Abel
 */
public class Exp extends BinaryOp {

    private ElementBase leftTerm;
    private ElementBase rightExp;

    public Exp(ElementBase left, ElementBase right) {
        super(left, right);
    }

    public ElementBase getLeftTerm() {
        return leftTerm;
    }

    public ElementBase getRightExp() {
        return rightExp;
    }

//    @Override
//    ElementBase typeCheck() {
//        return null;
//    }
//
//    @Override
//    public List<SemanticError> checkSemantics(Environment e) {
//        //initialize result variable
//        List<SemanticError> result = new ArrayList<SemanticError>();
//
//        if(leftTerm != null) result.addAll(leftTerm.checkSemantics(e));
//        if(rightExp !=null) result.addAll(rightExp.checkSemantics(e));
//
//        return result;
//    }
}
