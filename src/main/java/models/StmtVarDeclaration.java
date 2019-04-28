package models;

import util.Strings;
import util.TypeUtils;

import java.util.ArrayList;
import java.util.List;

public class StmtVarDeclaration extends Stmt {

    private String id;
    private Type type;
    private ElementBase exp;


    public StmtVarDeclaration(Type type, String id, ElementBase exp){
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    @Override
    public Type typeCheck() throws TypeCheckError {
        if (exp != null){
            TypeUtils.typeCheck(type, exp);
/*            if(!this.type.getClass().equals(this.exp.typeCheck().getClass())){
                throw new TypeCheckError("StmtVarDeclaration| "+this.type.getClass()+" and "+this.exp.typeCheck().getClass());
            }*/
        }
        return this.type.typeCheck();
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {
        //initialize result variable
        List<SemanticError> result = new ArrayList<SemanticError>();

        if (e.containsVariable(id)) {
            result.add(new SemanticError(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + id));
        } else {
            e.addVariable(id, new STentry(e.getNestingLevel(), type));
        }

        // check exp semantic
        result.addAll(exp.checkSemantics(e));

        return result;
    }
}
