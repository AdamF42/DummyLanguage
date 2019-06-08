package models;

import util.Strings;
import util.TypeUtils;

import java.util.ArrayList;
import java.util.List;

public class StmtVarDeclaration extends Stmt {

    private String id;
    private Type type;
    private Exp exp;


    public StmtVarDeclaration(Type type, String id, Exp exp){
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        if (exp != null){
            TypeUtils.typeCheck(type, exp);
        }
        return this.type.typeCheck();
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {
        //initialize result variable
        List<SemanticError> result = new ArrayList<>();

        if ((e.containsVariableLocal(id) && !e.getVariableValueLocal(id).isDeleted())||
                (e.containsFunction(id) && !e.getFunctionValue(id).isDeleted())) {
            result.add(new SemanticError(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + id));
        } else {
            e.addVariable(id, new STentry(e.getNestingLevel(), type, id));
            this.addrwAccess(e.getVariableValueLocal(id));
        }

        // check exp semantic
        result.addAll(exp.checkSemantics(e));
        this.addAllrwAccesses(exp.getRwAccesses());
        return result;
    }
}
