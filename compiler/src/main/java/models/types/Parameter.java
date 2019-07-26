package models.types;

import models.Environment;
import models.stentry.VarStEntry;
import util.SemanticError;
import util.Strings;

import java.util.ArrayList;
import java.util.List;

public class Parameter extends Type {

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
    public List<SemanticError> checkSemantics(Environment e) {

        ArrayList<SemanticError> res = new ArrayList<>();
        if (e.containsVariableLocal(id)||e.containsFunction(id)) {
            res.add(new SemanticError(Strings.ERROR_ALREADY_DECLARED_IDENTIFIER + id));
        } else {
            e.addVariable(id, new VarStEntry(e.getNestingLevel(), e.getIncrementOffset(), type, id));
        }

        return res;
    }

    @Override
    public String codeGeneration() {
        return Strings.EMPTY;
    }

    public String getId() {
        return id;
    }

    public TypeReferenceable getType() {
        return type;
    }

}
