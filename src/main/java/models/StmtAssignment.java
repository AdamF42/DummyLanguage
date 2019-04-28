package models;

import util.Strings;
import util.TypeUtils;

import java.util.ArrayList;
import java.util.List;

public class StmtAssignment extends Stmt{
    private Exp exp;
    private String id;
    private Type idType;

    /**
     * @param exp
     * @param id
     */
    public StmtAssignment(Exp exp, String id) {
        this.exp = exp;
        this.id = id;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        TypeUtils.typeCheck(idType, exp);
        return this.idType;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        //initialize result variable
        List<SemanticError> result = new ArrayList<SemanticError>();

        if (!e.containsVariable(id)) {
            result.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + id));
        } else {
            this.idType = e.getVariableValue(id).getType();
        }

        result.addAll(exp.checkSemantics(e));

        return result;

    }
}
