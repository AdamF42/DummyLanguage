package models;

import util.Strings;

import java.util.ArrayList;
import java.util.List;

public class Parameter extends ElementBase {

    private final TypeReferenceable type;
    private final String id;

    public Parameter(TypeReferenceable type, String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public Type typeCheck() {
        return null;
    }

    @Override
    List<SemanticError> checkSemantics(Environment e) {

        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if (e.containsVariableLocal(id)||e.containsFunction(id)) {
            res.add(new SemanticError(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + id));
        } else {
            e.addVariable(id, new STentry(e.getNestingLevel(), type, id));
        }

        return res;
    }

    public String getId() {
        return id;
    }

    public TypeReferenceable getType() {
        return type;
    }

}
