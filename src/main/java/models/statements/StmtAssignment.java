package models.statements;

import models.*;
import models.expressions.Exp;
import models.stentry.VarSTentry;
import models.types.Type;
import util.SemanticError;
import util.Strings;
import util.TypeCheckError;
import util.TypeUtils;

import java.util.ArrayList;
import java.util.List;

import static util.Strings.*;

public class StmtAssignment extends Stmt{
    private final Exp exp;
    private final String id;
    private int nl;
    private VarSTentry idEntry;


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
        TypeUtils.typeCheck(this.idEntry.getType(), exp);
        return this.idEntry.getType();
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
        return exp.codeGeneration() +
                loadW(AL,"0",FP) +
                getVariableForCgen(nl,idEntry)+
                storeW(ACC, Integer.toString(idEntry.getOffset()), AL);
    }

    private List<SemanticError>  checkIdSemantics(Environment e) {
        List<SemanticError> result = new ArrayList<>();

        if (!e.containsVariable(id)) {
            result.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + id));
        } else if (e.getVariableValue(id).isDeleted()) {
            result.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + id));
        }else {
            this.idEntry = e.getVariableValue(id);
            this.nl = e.getNestingLevel();
            this.addrwAccess(e.getVariableValue(id));
        }

        return result;
    }
}
