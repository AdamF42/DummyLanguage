package models;

import util.Strings;

import java.util.ArrayList;
import java.util.List;

public class ValueId extends ElementBase {

    private String id;
    private Type type;

    public ValueId(String id){
        this.id = id;
    }

    @Override
    public Type typeCheck() {
        return type;
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {

        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(!e.containsVariable(id)){
            res.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + id));
        } else {
            type = e.getVariableValue(id).getType();
        }

        return res;
    }
}
