package models.statements;


import models.Environment;
import models.expressions.Exp;
import models.types.Type;
import exeptions.TypeCheckException;
import util.SemanticError;

import java.util.ArrayList;
import java.util.List;
import static util.Strings.print;


public class StmtPrint extends Stmt{

    private final Exp exp;

    public StmtPrint(Exp exp) {
        this.exp =exp;
    }

    @Override
    public Type typeCheck() throws TypeCheckException {
        exp.typeCheck();
        return null;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        List<SemanticError> result = new ArrayList<>(exp.checkSemantics(e));
        this.addAllrwAccesses(exp.getRwAccesses());
        return result;
    }

    @Override
    public String codeGeneration() {
        return
                exp.codeGeneration()+
                print();
    }
}
