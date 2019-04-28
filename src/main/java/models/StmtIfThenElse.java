package models;

import java.util.ArrayList;
import java.util.List;

public class StmtIfThenElse extends Stmt {
    private ElementBase condition;
    private ElementBase ifBranch;
    private ElementBase thenBranch;

    private Type condType ;
    private Type ifBranchType;
    private Type thenBranchType;


    public StmtIfThenElse(ElementBase condition, ElementBase ifBranch, ElementBase thenBranch) {
        this.condition = condition;
        this.ifBranch = ifBranch;
        this.thenBranch = thenBranch;
    }


    @Override
    public Type typeCheck() throws TypeCheckError {
        return null;
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {
        //initialize result variable
        List<SemanticError> result = new ArrayList<SemanticError>();

        if (condition != null){
            result.addAll(condition.checkSemantics(e));
        }

        if (ifBranch!=null){
            result.addAll(ifBranch.checkSemantics(e));
        }

        if (thenBranch!=null){
            result.addAll(thenBranch.checkSemantics(e));
        }

        return result;
    }
}
