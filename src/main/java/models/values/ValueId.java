package models.values;

import models.Environment;
import models.stentry.VarSTentry;
import util.SemanticError;
import models.types.Type;
import util.Strings;

import java.util.ArrayList;
import java.util.List;

import static util.Strings.*;

public class ValueId extends Value {
    private int nl;
    private VarSTentry entry;


    public ValueId(String val) {
        super(val);
    }

    public String getId() {
        return this.getVal();
    }


    @Override
    public Type typeCheck() {
        return entry.getType();
    }

    @Override
    public List<SemanticError> checkSemantics(Environment e) {

        ArrayList<SemanticError> res = new ArrayList<>();
        if(!e.containsVariable(this.getVal())){
            res.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + this.getVal()));
        } else if (e.getVariableValue(this.getVal()).isDeleted()){
            res.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + this.getVal()));
        } else {
            this.entry=e.getVariableValue(this.getVal());
            this.nl=e.getNestingLevel();
            this.addrwAccess(this.entry);
        }

        return res;
    }

    @Override
    public String codeGeneration() {
        return loadW(AL,"0",FP) +
                getVariableForCgen(nl,entry)+
                loadW(ACC,Integer.toString(entry.getOffset()),AL);
    }
}
