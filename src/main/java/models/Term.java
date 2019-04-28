package models;

import java.util.ArrayList;
import java.util.List;

public class Term extends BinaryOp {

    private ElementBase leftTerm;
    private ElementBase rightExp;

    public Term(ElementBase left, ElementBase right) {
        super(left, right);
    }


//    @Override
//    ElementBase typeCheck() {
//        return null;
//    }
//
//    @Override
//    List<SemanticError> checkSemantics(Environment e) {
//
//        List<SemanticError> result = new ArrayList<SemanticError>(leftTerm.checkSemantics(e));
//
//        if (rightExp != null) result.addAll(rightExp.checkSemantics(e));
//
//        return result;
//    }
}
