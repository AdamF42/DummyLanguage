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

import static util.Strings.ACC;
import static util.Strings.FP;

public class StmtVarDeclaration extends Stmt {

    private final String id;
    private final Type type;
    private final Exp exp;
    private int offset;


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
    public List<SemanticError> checkSemantics(Environment e) {
        //initialize result variable
        List<SemanticError> result = new ArrayList<>();

        if ((e.containsVariableLocal(id) && !e.getVariableValueLocal(id).isDeleted())||
                (e.containsFunction(id) && !e.getFunctionValue(id).isDeleted())) {
            result.add(new SemanticError(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + id));
        } else {
            this.offset = e.getOffset();
            e.addVariable(id, new STentry(e.getNestingLevel(), e.getOffset(), type, id));
            this.addrwAccess(e.getVariableValueLocal(id));
        }

        // check exp semantic
        result.addAll(exp.checkSemantics(e));
        this.addAllrwAccesses(exp.getRwAccesses());
        return result;
    }

    @Override
    public String codeGeneration() {
        return exp.codeGeneration() + Strings.storeW(ACC, Integer.toString(offset), FP);
    }
}
