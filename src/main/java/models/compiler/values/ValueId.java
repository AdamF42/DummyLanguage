package models.compiler.values;

import models.compiler.Environment;
import models.compiler.stentry.VarStEntry;
import util.SemanticError;
import models.compiler.types.Type;
import util.Strings;

import java.util.ArrayList;
import java.util.List;

import static util.Strings.*;

public class ValueId extends Value {


    private VarStEntry entry;
    private int nl;

    public VarStEntry getEntry() {
        return entry;
    }

    public int getNestingLevel() {
        return nl;
    }



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
        //TODO rifattorizza i controlli semantici
        if(!e.containsVariable(this.getVal())){
            res.add(new SemanticError(Strings.ERROR_VARIABLE_DOESNT_EXIST + this.getVal()));
        } else if (e.getVariableValue(this.getVal()).isDeleted()){
            res.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + this.getVal()));
        } else if (e.isInsideVarDeclaration() ) {
            if (e.getDeclaredVariable().equals(this.getId()))
                res.add(new SemanticError(ERROR_VARIABLE_NOT_INITIALIZED + this.getVal()));
                //TODO: not DRY
                this.entry=e.getVariableValue(this.getVal());
        } else {
            //TODO: not DRY
            this.entry=e.getVariableValue(this.getVal());
            this.nl=e.getNestingLevel();
            this.addrwAccess(this.entry);
        }

        return res;
    }

    @Override
    public String codeGeneration() {
        if (nl==entry.getNestinglevel())
            return loadW(ACC, Integer.toString(entry.getOffset()), FP);
        return loadW(AL,"0",FP) +
                getVariableForCgen(nl,entry)+ //TODO: passa due interi invece di intero ed entry
                loadW(ACC,Integer.toString(entry.getOffset()),AL);
    }
}
