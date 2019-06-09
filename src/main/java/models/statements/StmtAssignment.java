package models.statements;

import models.*;
import models.expressions.Exp;
import models.types.Type;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;
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
        List<SemanticError> result = new ArrayList<>();

        //check id semantics
        result.addAll(checkIdSemantics(e));

        // check exp semantics
        result.addAll(exp.checkSemantics(e));
        this.addAllrwAccesses(exp.getRwAccesses());

        return result;

    }

    @Override
    public String codeGeneration() {
        return null;
    }

    private List<SemanticError>  checkIdSemantics(Environment e) {
        List<SemanticError> result = new ArrayList<>();

        if (!e.containsVariable(id)) {
            result.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + id));
        } else if (e.getVariableValue(id).isDeleted()) {
            result.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + id));
        }else {
            this.idType = e.getVariableValue(id).getType();
            this.addrwAccess(e.getVariableValue(id));
        }

        return result;
    }
}
