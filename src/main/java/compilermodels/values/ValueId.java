package compilermodels.values;

import compilermodels.Environment;
import compilermodels.stentry.VarStEntry;
import util.SemanticError;
import compilermodels.types.Type;
import util.Strings;

import java.util.ArrayList;
import java.util.List;

import static util.Strings.*;

public class ValueId extends Value {
    private int nl;
    private VarStEntry entry;


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
            res.add(new SemanticError(Strings.ERROR_VARIABLE_HAS_BEEN_DELETED + this.getVal())); //TODO rifattorizza i controlli semantici
        } else if (e.getIsInsideVarDeclaration()) {
            if (e.getDeclaredVariable().equals(this.getId()))
                res.add(new SemanticError(ERROR_VARIABLE_NOT_INITIALIZED + this.getVal()));
        } else {
            this.entry=e.getVariableValue(this.getVal());
            this.nl=e.getNestingLevel();
            this.addrwAccess(this.entry);
        }

        return res;
    }

    @Override
    public String codeGeneration() {
        if (nl==entry.getNestinglevel())
            return loadW(ACC,Integer.toString(entry.getOffset()),FP);
        return loadW(AL,"0",FP) +
                getVariableForCgen(nl,entry)+ //TODO: passa due interi invece di intero ed entry
                loadW(ACC,Integer.toString(entry.getOffset()),AL);
    }
}
