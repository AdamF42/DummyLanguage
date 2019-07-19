package models.compiler.statements;


import models.compiler.*;
import models.compiler.expressions.Exp;
import models.compiler.types.Type;
import util.SemanticError;
import util.TypeCheckException;
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
