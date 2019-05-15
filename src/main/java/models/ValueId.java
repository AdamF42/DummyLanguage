package models;

import util.Strings;

import java.util.ArrayList;
import java.util.List;

public class ValueId extends Value {
    private String line;
    private String charPos;

    public ValueId(String val, String line, String charPos) {
        super(val);
        this.line = line;
        this.charPos = charPos;
    }

    public String getId() {
        return this.getVal();
    }

    private Type type;


    @Override
    public Type typeCheck() {
        return type;
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        ArrayList<SemanticError> res = new ArrayList<>();

        if(!e.containsVariable(this.getVal())){
            res.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + this.getVal()));
        } else if (e.getVariableValue(this.getVal()).isDeleted()){
            res.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + this.getVal()));
        } else {
            type = e.getVariableValue(this.getVal()).getType();
            this.addrwAccess(e.getVariableValue(this.getVal()));
        }

        return res;
    }
}
