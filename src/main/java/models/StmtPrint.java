package models;


import java.util.ArrayList;
import java.util.List;

public class StmtPrint extends Stmt{

    private Exp exp;

    public StmtPrint(Exp exp) {
        this.exp =exp;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        return exp.typeCheck();
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>(exp.checkSemantics(e));
        this.addAllrwAccesses(exp.getRwAccesses());
        return result;
    }
}
