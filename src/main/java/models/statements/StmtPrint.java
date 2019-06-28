package models.statements;


import models.*;
import models.expressions.Exp;
import models.types.Type;
import util.SemanticError;
import util.TypeCheckError;
import java.util.ArrayList;
import java.util.List;
import static util.Strings.print;


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
