package models.values;

import models.Environment;
import util.SemanticError;
import models.types.Type;
import util.Strings;

import java.util.ArrayList;
import java.util.List;

import static util.Strings.*;

public class ValueId extends Value {
    private String line;
    private String charPos;
    private int offset;

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
            this.offset=e.getVariableValue(this.getVal()).getOffset();
        }

        return res;
    }

    @Override
    public String codeGeneration() {
        return loadW(ACC,Integer.toString(offset),FP);
    }
}
